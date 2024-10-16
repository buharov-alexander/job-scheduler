package com.bukharov.scheduler.task_runner_service.runner;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.bukharov.scheduler.task_runner_service.repository.TaskEntity;
import com.bukharov.scheduler.task_runner_service.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class TaskRunnerImpl implements TaskRunner {

	public static final int ONE_MINUTE_IN_MILLISECONDS = 60000;

	@Autowired
	private TaskRepository taskRepository;

	@Scheduled(fixedRate = ONE_MINUTE_IN_MILLISECONDS)
	public void scheduleFixedRateTask() {

		List<TaskEntity> tasks = taskRepository.findByNextExecutionTimeLessThan(ZonedDateTime.now());
		System.out.println(String.format("Tasks for run: [%s]", tasks.stream().map(TaskEntity::name).collect(Collectors.joining())));
	}
}
