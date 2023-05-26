package com.memariyan.metricsimulator.spec.dto.value;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ConstantValue<T> extends Value<T> {

	@NotNull
	private T content;

}
