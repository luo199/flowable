package com.huasisoft.flow;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableAsync
public class AsyncAndScheduleConfig implements AsyncConfigurer,SchedulingConfigurer{

	@Value("${scheduleExector.pool.size}")  
	int schedulePoolSize;  
	@Value("${threadPool.min}")  
	int threadPoolMin;  
	@Value("${threadPool.max}")  
	int threadPoolMax;
	@Value("${threadPool.queueSize}")  
	int threadPoolQueueSize;
	@Value("${threadPool.keepAlive}")  
	int threadPoolKeepAlive;
	
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolMin);
        executor.setMaxPoolSize(threadPoolMax);
        executor.setQueueCapacity(threadPoolQueueSize);
        executor.setKeepAliveSeconds(threadPoolKeepAlive);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.setThreadNamePrefix("easyofficeExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
	}

	@Override 
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setPoolSize(schedulePoolSize);
		threadPoolTaskScheduler.setThreadNamePrefix("easyofficeScheduler-");
		threadPoolTaskScheduler.setAwaitTerminationSeconds(60);
		threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
		threadPoolTaskScheduler.initialize();
		taskRegistrar.setScheduler(threadPoolTaskScheduler);
	}
	
	
	@Bean(name="threadPoolTaskExecutor")
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(threadPoolMin);
        executor.setMaxPoolSize(threadPoolMax);
        executor.setQueueCapacity(threadPoolQueueSize);
        executor.setKeepAliveSeconds(threadPoolKeepAlive);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.setThreadNamePrefix("easyofficeThreadPoolExecutor-");
        executor.initialize();
        return executor;
	}
	
}
