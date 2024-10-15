package com.bukharov.scheduler.task_runner_service.runner;

import org.springframework.scheduling.annotation.Scheduled;

public class TaskRunnerImpl implements TaskRunner {

	public static final int ONE_MINUTE_IN_MILLISECONDS = 60000;

	@Scheduled(fixedRate = ONE_MINUTE_IN_MILLISECONDS)
	public void scheduleFixedRateTask() {
		System.out.println(
				"Fixed rate task - " + System.currentTimeMillis() / 1000);
	}
}
