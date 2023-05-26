package com.memariyan.metricsimulator.service.recorder.impl;

import com.memariyan.metricsimulator.exception.InvalidMetricException;
import com.memariyan.metricsimulator.model.Metric;
import com.memariyan.metricsimulator.model.MetricType;
import com.memariyan.metricsimulator.service.recorder.MetricRecorder;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by mohammad on 3/30/20
 * Mail To memariyan74@gmail.com
 */
@Component
@RequiredArgsConstructor
public class GaugeMetricRecorder implements MetricRecorder {

	private final MeterRegistry registry;

	private final Map<String, AtomicInteger> previousValues = new HashMap<>();

	@Override
	public void record(Metric metric) throws InvalidMetricException {

		if (Objects.isNull(metric.getValue())) {
			throw new InvalidMetricException("gauge metric need to have a value!", metric);
		}

		List<Tag> tags = getTags(metric);
		StringBuilder tagCollectionText = new StringBuilder().append(metric.getName()).append("@");
		tags.forEach(tag -> tagCollectionText.append(tag.getKey()).append("#").append(tag.getValue()).append("#"));
		String tagCollectionId = tagCollectionText.toString();

		if (!previousValues.containsKey(tagCollectionId)) {
			AtomicInteger atomicValue = new AtomicInteger(metric.getValue().intValue());
			previousValues.put(tagCollectionId, atomicValue);
			registry.gauge(metric.getName(), tags, atomicValue);
		} else {
			previousValues.get(tagCollectionId).set(metric.getValue().intValue());
		}
	}

	@Override
	public MetricType metricType() {
		return MetricType.GAUGE;
	}
}
