package com.memariyan.metricsimulator.api.web.validator;

import com.memariyan.metricsimulator.spec.InsertMetricRequest;
import com.memariyan.metricsimulator.spec.dto.MetricType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.security.InvalidParameterException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class InsertMetricRequestValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return InsertMetricRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InsertMetricRequest request = (InsertMetricRequest) target;
		request.getGroups().forEach(group -> group.getMetrics().forEach(metric -> {
			if (!metric.getType().equals(MetricType.COUNTER) && Objects.isNull(metric.getValue())) {
				throw new InvalidParameterException("empty value only allowed for counter metrics! please check value exist for another metric types.");
			}
		}));
	}

}
