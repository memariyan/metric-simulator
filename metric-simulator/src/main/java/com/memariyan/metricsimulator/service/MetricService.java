package com.memariyan.metricsimulator.service;

import com.memariyan.metricsimulator.exception.InvalidMetricException;
import com.memariyan.metricsimulator.model.Metric;
import com.memariyan.metricsimulator.spec.dto.config.MetricGroupConfig;

import java.util.List;

/**
 * created by mohammad on 3/30/20
 * Mail To memariyan74@gmail.com
 */
public interface MetricService {

	void insert(Metric metric) throws InvalidMetricException;

	void insert(List<Metric> metrics);

	List<Metric> generate(MetricGroupConfig config);

}
