package com.memariyan.metricsimulator.spec;

import java.util.List;

import com.memariyan.metricsimulator.spec.dto.MetricGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateMetricResponse {

	private List<MetricGroup> groups;
}
