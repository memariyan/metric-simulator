package com.memariyan.metricsimulator.spec.dto.value;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
//TODO: set validation with anotation on users
public class WeightedValue<T> extends Value<T> {

	@Valid
	@NotEmpty
	private List<T> contents;

	@NotEmpty
	private List<Double> weights;
}
