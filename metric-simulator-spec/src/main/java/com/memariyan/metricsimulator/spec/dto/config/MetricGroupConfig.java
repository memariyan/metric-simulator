package com.memariyan.metricsimulator.spec.dto.config;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class MetricGroupConfig {

	@NotEmpty
	private String groupName;

	@Valid
	@NotEmpty
	private List<MetricConfig> partitionConfigs;

}
