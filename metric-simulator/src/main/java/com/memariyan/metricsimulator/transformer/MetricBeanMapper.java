package com.memariyan.metricsimulator.transformer;

import com.memariyan.metricsimulator.model.Metric;
import com.memariyan.metricsimulator.model.MetricType;
import com.memariyan.metricsimulator.spec.dto.MetricDto;
import com.memariyan.metricsimulator.spec.dto.MetricGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MetricBeanMapper {

	@ValueMappings({
			@ValueMapping(target = "COUNTER", source = "COUNTER"),
			@ValueMapping(target = "SUMMARY", source = "SUMMARY"),
			@ValueMapping(target = "TIMER", source = "TIMER"),
			@ValueMapping(target = "GAUGE", source = "GAUGE"),
			@ValueMapping(target = MappingConstants.NULL, source = MappingConstants.ANY_REMAINING)
	})
	MetricType toMetricType(com.memariyan.metricsimulator.spec.dto.MetricType type);

	@Mapping(target = "type", source = "metric.type")
	@Mapping(target = "name", source = "metric.name")
	@Mapping(target = "tags", source = "metric.tags")
	@Mapping(target = "value", source = "metric.value")
	@Mapping(target = "pauseDuration", source = "metric.pauseDuration")
	Metric toMetric(MetricDto metric, String groupName);

	default List<Metric> toMetrics(MetricGroup metricGroup) {
		if (!CollectionUtils.isEmpty(metricGroup.getMetrics())) {
			return metricGroup.getMetrics().stream()
					.map(metricDto -> toMetric(metricDto, metricGroup.getName()))
					.collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	MetricDto toMetricDto(Metric metric);

	List<MetricDto> toMetricDtoList(List<Metric> metrics);

	default MetricGroup toMetricGroup(List<Metric> metrics) {
		return !CollectionUtils.isEmpty(metrics) ?
				new MetricGroup(metrics.get(0).getGroupName(), toMetricDtoList(metrics)) :
				new MetricGroup();
	}

}
