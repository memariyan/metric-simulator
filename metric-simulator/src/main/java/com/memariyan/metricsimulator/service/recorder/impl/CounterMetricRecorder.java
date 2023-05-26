package com.memariyan.metricsimulator.service.recorder.impl;

import com.memariyan.metricsimulator.exception.InvalidMetricException;
import com.memariyan.metricsimulator.model.Metric;
import com.memariyan.metricsimulator.model.MetricType;
import com.memariyan.metricsimulator.service.recorder.MetricRecorder;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * created by mohammad on 3/30/20
 * Mail To memariyan74@gmail.com
 */
@Component
@RequiredArgsConstructor
public class CounterMetricRecorder implements MetricRecorder {

	private final MeterRegistry registry;

	@Override
	public void record(Metric metric) throws InvalidMetricException {
		registry.counter(metric.getName(), getTags(metric)).increment();
	}

	@Override
	public MetricType metricType() {
		return MetricType.COUNTER;
	}
}
