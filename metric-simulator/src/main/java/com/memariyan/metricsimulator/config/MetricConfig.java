package com.memariyan.metricsimulator.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MetricConfig {

	private final Environment environment;

	public MetricConfig(Environment environment) {
		this.environment = environment;
	}

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return registry -> registry.config()
				.commonTags("application", this.environment.getRequiredProperty("spring.application.name"));
	}

}