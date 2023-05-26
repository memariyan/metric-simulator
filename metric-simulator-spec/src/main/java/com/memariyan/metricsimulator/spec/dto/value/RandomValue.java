package com.memariyan.metricsimulator.spec.dto.value;

import lombok.Data;

@Data
public class RandomValue<T extends Number> extends Value<T> {

	private T min;

	private T max;

	private Double precision;

}
