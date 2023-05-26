package com.memariyan.metricsimulator.spec.dto;

import com.memariyan.metricsimulator.spec.dto.value.WeightedRandomValue;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MetricTagValue {

	@NotEmpty
	private String content;

	@NotNull
	private WeightedRandomValue value;

}
