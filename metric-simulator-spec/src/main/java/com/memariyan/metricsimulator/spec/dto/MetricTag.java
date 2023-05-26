package com.memariyan.metricsimulator.spec.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MetricTag {

	@NotEmpty
	private String name;

	@NotEmpty
	private String value;

}
