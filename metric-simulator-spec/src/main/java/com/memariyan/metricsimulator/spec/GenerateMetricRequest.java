package com.memariyan.metricsimulator.spec;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.memariyan.metricsimulator.spec.dto.config.MetricGroupConfig;
import lombok.Data;

@Data
public class GenerateMetricRequest {

	@Valid
	@NotEmpty
	private List<MetricGroupConfig> groupConfigs;
}
