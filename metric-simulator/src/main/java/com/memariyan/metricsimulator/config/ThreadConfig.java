package com.memariyan.metricsimulator.config;

import com.memariyan.metricsimulator.util.ConfigProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * created by mohammad on 3/30/20
 * Mail To memariyan74@gmail.com
 */
@Configuration
@RequiredArgsConstructor
public class ThreadConfig {

	private final ConfigProvider configs;

	@Bean
	@Qualifier("metricThreadPoolTaskExecutor")
	public TaskExecutor metricThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(configs.getThreadPoolCoreSize());
		executor.setMaxPoolSize(configs.getThreadPoolMaxSize());
		executor.setThreadNamePrefix("metric_task_executor_thread");
		executor.initialize();
		return executor;
	}
}
