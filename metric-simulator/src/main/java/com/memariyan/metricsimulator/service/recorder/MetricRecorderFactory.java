package com.memariyan.metricsimulator.service.recorder;

import com.memariyan.metricsimulator.model.MetricType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * created by mohammad on 3/30/20
 * Mail To memariyan74@gmail.com
 */
@Component
public class MetricRecorderFactory {

	private Map<MetricType, MetricRecorder> recordersMap = new EnumMap<>(MetricType.class);

	public MetricRecorderFactory(List<MetricRecorder> recorders) {
		recorders.forEach(metricRecorder ->
				this.recordersMap.put(metricRecorder.metricType(), metricRecorder));
	}

	public MetricRecorder getMetricRecorder(MetricType type) {
		if (!recordersMap.containsKey(type)) {
			throw new IllegalArgumentException("not found metric recorder for metric type=" + type);
		}
		return recordersMap.get(type);
	}

}
