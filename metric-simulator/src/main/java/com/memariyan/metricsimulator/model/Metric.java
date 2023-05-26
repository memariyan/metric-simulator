package com.memariyan.metricsimulator.model;

import lombok.Data;

import java.util.List;

@Data
public class Metric {

	private String groupName;

	private MetricType type;

	private String name;

	private List<Tag> tags;

	private Double value;

	private Long pauseDuration;

}
