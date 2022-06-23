package pl.mariuszk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
public class TaskSchedulerConfiguration {

	@Bean
	public TaskScheduler registerTaskScheduler() {
		return new ConcurrentTaskScheduler();
	}
}
