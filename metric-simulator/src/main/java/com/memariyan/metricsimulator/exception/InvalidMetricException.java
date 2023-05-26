package com.memariyan.metricsimulator.exception;

import java.security.InvalidParameterException;

import lombok.Data;

import com.memariyan.metricsimulator.model.Metric;

@Data
public class InvalidMetricException extends InvalidParameterException {

	private Metric metric;

	public InvalidMetricException(String message, Metric metric) {
		super(message);
		this.metric = metric;
	}
}
