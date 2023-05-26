package com.memariyan.metricsimulator.service.recorder.impl;

import com.memariyan.metricsimulator.exception.InvalidMetricException;
import com.memariyan.metricsimulator.model.Metric;
import com.memariyan.metricsimulator.model.MetricType;
import com.memariyan.metricsimulator.service.recorder.MetricRecorder;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * created by mohammad on 3/30/20
 * Mail To memariyan74@gmail.com
 */
@Component
@RequiredArgsConstructor
public class TimerMetricRecorder implements MetricRecorder {

	private final MeterRegistry registry;

	@Override
	public void record(Metric metric) throws InvalidMetricException {
		if (Objects.isNull(metric.getValue())) {
			throw new InvalidMetricException("timer metric need to have a duration value!", metric);
		}
		registry.timer(metric.getName(), getTags(metric)).record(metric.getValue().longValue(), TimeUnit.MILLISECONDS);
	}

	@Override
	public MetricType metricType() {
		return MetricType.TIMER;
	}
}
