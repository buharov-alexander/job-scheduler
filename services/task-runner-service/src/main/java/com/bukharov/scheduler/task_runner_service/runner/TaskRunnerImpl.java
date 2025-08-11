package com.bukharov.scheduler.task_runner_service.runner;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.bukharov.scheduler.task_runner_service.kafka.TaskKafkaProvider;
import com.bukharov.scheduler.task_runner_service.kafka.TaskMessage;
import com.bukharov.scheduler.task_runner_service.repository.TaskEntity;
import com.bukharov.scheduler.task_runner_service.repository.TaskRepository;
import com.bukharov.scheduler.task_runner_service.repository.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class TaskRunnerImpl implements TaskRunner {

	public static final int ONE_MINUTE_IN_MILLISECONDS = 60000;

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskKafkaProvider kafkaProvider;

	@Scheduled(fixedDelay = ONE_MINUTE_IN_MILLISECONDS)
	public void scheduleFixedRateTask() {
		List<TaskEntity> tasks =
				taskRepository.findByNextExecutionTimeLessThanAndStatus(ZonedDateTime.now(), TaskStatus.SCHEDULED);
		System.out.printf("Tasks for run: [%s]%n", tasks.stream().map(TaskEntity::name).collect(Collectors.joining()));

		tasks.forEach(task -> {
			System.out.printf("Activate task: %s%n", task.name());
			kafkaProvider.sendMessage(new TaskMessage(task.id(), task.name(), task.traceId()));

			task.status(TaskStatus.ACTIVE);
			taskRepository.save(task);
		});
	}
}
