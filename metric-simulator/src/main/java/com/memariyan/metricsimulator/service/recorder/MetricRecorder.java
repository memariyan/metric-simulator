package com.memariyan.metricsimulator.service.recorder;

import java.util.List;
import java.util.stream.Collectors;

import io.micrometer.core.instrument.Tag;

import org.springframework.util.CollectionUtils;

import com.memariyan.metricsimulator.exception.InvalidMetricException;
import com.memariyan.metricsimulator.model.Metric;
import com.memariyan.metricsimulator.model.MetricType;

public interface MetricRecorder {

	void record(Metric metric) throws InvalidMetricException;

	MetricType metricType();

	default List<Tag> getTags(Metric metric) throws InvalidMetricException {

		if (CollectionUtils.isEmpty(metric.getTags())) {
			throw new InvalidMetricException("there is no tag in metric entity!", metric);
		}
		return metric.getTags().stream()
				.map(tag -> Tag.of(tag.getName(), tag.getValue()))
				.collect(Collectors.toList());
	}

}
