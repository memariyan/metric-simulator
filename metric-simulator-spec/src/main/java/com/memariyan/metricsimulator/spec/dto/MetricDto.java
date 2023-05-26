package com.memariyan.metricsimulator.spec.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MetricDto {

	@NotNull
	private MetricType type;

	@NotEmpty
	private String name;

	@Valid
	@NotEmpty
	private List<MetricTag> tags;

	@DecimalMin("0")
	private Double value;

	@DecimalMin("0")
	private Long pauseDuration;

	public Long getPauseDuration() {
		return (pauseDuration == null) ? 0L : pauseDuration;
	}

}
