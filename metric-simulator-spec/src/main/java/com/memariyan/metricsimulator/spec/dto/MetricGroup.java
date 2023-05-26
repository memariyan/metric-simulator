package com.memariyan.metricsimulator.spec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetricGroup {

	private String name;

	@Valid
	@NotEmpty
	private List<MetricDto> metrics;

}
