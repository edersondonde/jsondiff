package br.com.edonde.jsondiff.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * TaskExecutor configuration class
 */
@Configuration
public class ThreadConfig {

    /**
     * Configures and returns a {@link TaskExecutor}
     *
     * @return A configured instance of {@link TaskExecutor}
     */
    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setThreadNamePrefix("task_executor_main_thread");
        executor.initialize();
        return executor;
    }
}