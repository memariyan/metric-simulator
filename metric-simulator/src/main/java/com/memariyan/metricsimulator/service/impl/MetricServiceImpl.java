package com.memariyan.metricsimulator.service.impl;

import com.memariyan.metricsimulator.exception.InvalidMetricConfigException;
import com.memariyan.metricsimulator.exception.InvalidMetricException;
import com.memariyan.metricsimulator.model.Metric;
import com.memariyan.metricsimulator.model.MetricType;
import com.memariyan.metricsimulator.model.Tag;
import com.memariyan.metricsimulator.service.MetricService;
import com.memariyan.metricsimulator.service.recorder.MetricRecorderFactory;
import com.memariyan.metricsimulator.spec.dto.config.MetricConfig;
import com.memariyan.metricsimulator.spec.dto.config.MetricGroupConfig;
import com.memariyan.metricsimulator.spec.dto.config.MetricTagConfig;
import com.memariyan.metricsimulator.spec.dto.value.ConstantValue;
import com.memariyan.metricsimulator.spec.dto.value.RandomValue;
import com.memariyan.metricsimulator.spec.dto.value.Value;
import com.memariyan.metricsimulator.spec.dto.value.WeightedRandomValue;
import com.memariyan.metricsimulator.spec.dto.value.WeightedValue;
import com.memariyan.metricsimulator.transformer.MetricBeanMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MetricServiceImpl implements MetricService {

	private final TaskExecutor taskExecutor;

	private final MetricRecorderFactory recorderFactory;

	private final MetricBeanMapper metricMapper;

	public MetricServiceImpl(@Qualifier("metricThreadPoolTaskExecutor") TaskExecutor taskExecutor, MetricRecorderFactory recorderFactory, MetricBeanMapper metricMapper) {
		this.taskExecutor = taskExecutor;
		this.recorderFactory = recorderFactory;
		this.metricMapper = metricMapper;
	}

	@Override
	public void insert(Metric metric) throws InvalidMetricException {
		recorderFactory.getMetricRecorder(metric.getType()).record(metric);
		logger.info("metric={} inserted successfully", metric);
	}

	@Override
	public void insert(List<Metric> metrics) {
		taskExecutor.execute(() -> metrics.forEach(metric -> {
			try {
				insert(metric);
				if (metric.getPauseDuration() != null && metric.getPauseDuration() > 0) {
					Thread.sleep(metric.getPauseDuration());
				}
			} catch (InvalidMetricException ex) {
				logger.error(ex.getMessage() + " - metric entity=" + metric, ex);
			} catch (InterruptedException ex) {
				logger.error("metric pause error : ", ex);
			}
		}));
	}

	@SneakyThrows
	@Override
	public List<Metric> generate(MetricGroupConfig config) {

		List<Metric> result = new ArrayList<>();
		for (MetricConfig partitionConfig : config.getPartitionConfigs()) {
			for (int i = 0; i < partitionConfig.getCount(); i++) {

				List<Tag> tags = new ArrayList<>();
				for (MetricTagConfig tagConfig : partitionConfig.getMetricTags()) {
					tags.add(new Tag(tagConfig.getName(), generateTextValue(tagConfig.getValue())));
				}
				Metric metric = new Metric();
				metric.setType(metricMapper.toMetricType(partitionConfig.getMetricType()));
				metric.setGroupName(config.getGroupName());
				metric.setName(partitionConfig.getMetricName());
				metric.setPauseDuration(generateNumberValue(partitionConfig.getPauseDuration()).longValue());
				metric.setTags(tags);

				if (!metric.getType().equals(MetricType.COUNTER)) {
					if (Objects.isNull(partitionConfig.getMetricValue())) {
						throw new InvalidMetricConfigException("empty value only allowed for counter metrics! please check value exist for another metric types.");
					}
					metric.setValue(generateNumberValue(partitionConfig.getMetricValue()));
				}
				result.add(metric);
			}
		}
		return result;
	}

	private <N extends Number> Double generateNumberValue(Value<N> metricValue) throws InvalidMetricConfigException {
		Optional<N> result = generateValue(metricValue);
		return result.map(n -> (Double) n).orElseGet(() -> generateRandomValue(metricValue));
	}

	private String generateTextValue(Value<String> metricValue) throws InvalidMetricConfigException {
		return generateValue(metricValue).orElseThrow(() -> new InvalidMetricConfigException("invalid value as text, value=" + metricValue));
	}

	private <T> Optional<T> generateValue(Value<T> metricValue) throws InvalidMetricConfigException {
		if (metricValue.getClass().isAssignableFrom(ConstantValue.class)) {
			return Optional.of(((ConstantValue<T>) metricValue).getContent());
		}
		if (metricValue.getClass().isAssignableFrom(WeightedValue.class)) {
			WeightedValue<T> value = (WeightedValue<T>) metricValue;
			if (value.getContents().size() != value.getWeights().size()) {
				throw new InvalidMetricConfigException("invalid value (contents size & weights size not match), value=" + value);
			}
			return Optional.of(getWeightedValue(value.getContents(), value.getWeights()));
		}
		return Optional.empty();
	}

	private <N extends Number> Double generateRandomValue(Value<N> metricValue) throws InvalidMetricConfigException {

		if (metricValue.getClass().isAssignableFrom(RandomValue.class)) {
			return getRandomValue((RandomValue<N>) metricValue);
		}
		if (metricValue.getClass().isAssignableFrom(WeightedRandomValue.class)) {
			WeightedRandomValue<Double> value = (WeightedRandomValue<Double>) metricValue;
			if (value.getBounds().size() != value.getWeights().size()) {
				throw new InvalidMetricConfigException("invalid value (bounds size & weights size not match), value=" + value);
			}
			return getWeightedRandomValue(value.getBounds(), value.getWeights());
		}
		throw new InvalidMetricConfigException("unsupported metric value = " + metricValue);
	}

	private <T> T getWeightedValue(List<T> values, List<Double> weights) {

		List<Pair<T, Double>> result = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			result.add(Pair.create(values.get(i), weights.get(i)));
		}
		EnumeratedDistribution<T> distribution = new EnumeratedDistribution<T>(result);
		return distribution.sample();
	}

	private <N extends Number> Double random(N min, N max, double precision) {
		double number = new Random().nextInt((int) ((max.doubleValue() - min.doubleValue()) * precision + 1)) + min.doubleValue() * precision;
		return number / precision;
	}

	@SneakyThrows
	private <N extends Number> Double getRandomValue(RandomValue<N> value) {
		double min = value.getMin() != null ? value.getMin().doubleValue() : Double.MIN_VALUE;
		double max = value.getMax() != null ? value.getMax().doubleValue() : Double.MAX_VALUE;

		double precision = 1D;
		if (value.getPrecision() != null) {
			if (value.getPrecision() > 0 && value.getPrecision() <= 1) {
				precision = 1 / value.getPrecision();
			} else {
				throw new InvalidMetricConfigException("invalid precision for value = " + value);
			}
		}
		return random(min, max, precision);
	}

	private <N extends Number> Double getWeightedRandomValue(List<RandomValue<N>> bounds, List<Double> weights) {
		List<Double> randomValues = bounds.stream().map(this::getRandomValue).collect(Collectors.toList());
		return getWeightedValue(randomValues, weights);
	}

}
