package com.memariyan.metricsimulator.spec;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.memariyan.metricsimulator.spec.dto.MetricGroup;
import lombok.Data;

@Data
public class InsertMetricRequest {

	@Valid
	@NotEmpty
	private List<MetricGroup> groups;
}
