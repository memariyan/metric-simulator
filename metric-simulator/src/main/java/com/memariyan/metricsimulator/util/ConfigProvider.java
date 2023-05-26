package com.memariyan.metricsimulator.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConfigProvider {

	private final Environment environment;

	public Integer getThreadPoolCoreSize() {
		return environment.getProperty("metric.thread.pool.core.size", Integer.class);
	}

	public Integer getThreadPoolMaxSize() {
		return environment.getProperty("metric.thread.pool.max.size", Integer.class);
	}


}
