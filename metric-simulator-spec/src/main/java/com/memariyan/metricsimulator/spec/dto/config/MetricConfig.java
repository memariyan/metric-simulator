package com.memariyan.metricsimulator.spec.dto.config;

import com.memariyan.metricsimulator.spec.dto.MetricType;
import com.memariyan.metricsimulator.spec.dto.value.Value;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class MetricConfig {

	@NotNull
	private MetricType metricType;

	@NotEmpty
	private String metricName;

	@Valid
	private Value<Double> metricValue;

	@Valid
	@NotEmpty
	private List<MetricTagConfig> metricTags;

	@NotNull
	private Integer count;

	@Valid
	@NotNull
	private Value<Long> pauseDuration;

}
