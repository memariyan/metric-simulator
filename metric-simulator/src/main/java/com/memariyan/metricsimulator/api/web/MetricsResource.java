package com.memariyan.metricsimulator.api.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.memariyan.metricsimulator.service.MetricService;
import com.memariyan.metricsimulator.spec.GenerateMetricRequest;
import com.memariyan.metricsimulator.spec.GenerateMetricResponse;
import com.memariyan.metricsimulator.spec.InsertMetricRequest;
import com.memariyan.metricsimulator.spec.dto.MetricGroup;
import com.memariyan.metricsimulator.transformer.MetricBeanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memariyan.metricsimulator.api.web.validator.InsertMetricRequestValidator;
import com.memariyan.metricsimulator.model.Metric;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/metrics")
public class MetricsResource {

	private final MetricBeanMapper metricMapper;

	private final InsertMetricRequestValidator requestValidator;

	private final MetricService metricService;

	@InitBinder("insertMetricRequest")
	public void insertMetricRequestValidator(WebDataBinder binder) {
		binder.addValidators(requestValidator);
	}

	@PostMapping(path = "/generate", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<GenerateMetricResponse> generateMetrics(@Valid @RequestBody GenerateMetricRequest request) {

		List<MetricGroup> groupList = request.getGroupConfigs()
				.stream().map(groupConfig -> metricMapper.toMetricGroup(metricService.generate(groupConfig)))
				.collect(Collectors.toList());
		return ResponseEntity.ok(new GenerateMetricResponse(groupList));
	}

	@PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<GenerateMetricResponse> generateAndInsertMetrics(@Valid @RequestBody GenerateMetricRequest request) {

		List<MetricGroup> groupList = new ArrayList<>();
		request.getGroupConfigs().forEach(groupConfig -> {
			List<Metric> metrics = metricService.generate(groupConfig);
			metricService.insert(metrics);
			groupList.add(metricMapper.toMetricGroup(metrics));
		});
		return ResponseEntity.ok(new GenerateMetricResponse(groupList));
	}

	@PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<String> insertMetrics(@Valid @RequestBody InsertMetricRequest insertMetricRequest) {

		insertMetricRequest.getGroups().forEach(metricGroup -> metricService.insert(metricMapper.toMetrics(metricGroup)));
		return ResponseEntity.ok("");
	}
}
