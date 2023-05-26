package com.memariyan.metricsimulator.spec.dto.config;

import com.memariyan.metricsimulator.spec.dto.value.Value;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MetricTagConfig {

	@NotEmpty
	private String name;

	@NotNull
	@Valid
	private Value<String> value;
}
