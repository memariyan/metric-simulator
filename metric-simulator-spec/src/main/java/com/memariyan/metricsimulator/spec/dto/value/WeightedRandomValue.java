package com.memariyan.metricsimulator.spec.dto.value;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class WeightedRandomValue<T extends Number> extends Value<T> {

	@Valid
	@NotEmpty
	private List<RandomValue<T>> bounds;

	@NotEmpty
	private List<Double> weights;
}
