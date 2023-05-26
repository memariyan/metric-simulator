package com.memariyan.metricsimulator.spec.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MetricType {

	COUNTER(0), SUMMARY(1), GAUGE(2), TIMER(3);

	private final int value;

	MetricType(int value) {
		this.value = value;
	}

	@JsonCreator
	public static MetricType fromValue(int i) {

		for (MetricType type : MetricType.values()) {
			if (type.value == i)
				return type;
		}
		throw new IllegalStateException("undefined value found for metric type " + i);
	}

	@JsonValue
	public int toValue() {
		return value;
	}
}
