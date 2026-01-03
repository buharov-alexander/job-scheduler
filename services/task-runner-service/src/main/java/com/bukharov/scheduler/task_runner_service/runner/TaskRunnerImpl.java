package com.bukharov.scheduler.task_runner_service.runner;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.bukharov.scheduler.task_runner_service.kafka.TaskKafkaProvider;
import com.bukharov.scheduler.task_runner_service.kafka.TaskMessage;
import com.bukharov.scheduler.task_runner_service.repository.TaskEntity;
import com.bukharov.scheduler.task_runner_service.repository.TaskRepository;
import com.bukharov.scheduler.task_runner_service.repository.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
public class TaskRunnerImpl implements TaskRunner {

	public static final int TWENTY_SECONDS_IN_MILLISECONDS = 20000;

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private TaskKafkaProvider kafkaProvider;

	/**
	 * Periodically checks tasks ready for execution and sends them to Kafka.
	 * Uses atomic status update to prevent race condition
	 * when multiple instances of the service are running.
	 */
	@Scheduled(fixedDelay = TWENTY_SECONDS_IN_MILLISECONDS)
	public void scheduleFixedRateTask() {
		ZonedDateTime now = ZonedDateTime.now();
		
		// Atomic selection and locking of tasks for processing
		List<TaskEntity> tasks = taskRepository.findAndLockTasksForExecution(now, TaskStatus.SCHEDULED.name());
		
		if (tasks.isEmpty()) {
			log.debug("No tasks ready for execution");
			return;
		}

		log.info("Found {} tasks ready for execution: [{}]", 
				tasks.size(),
				tasks.stream().map(TaskEntity::name).collect(Collectors.joining(", ")));

		for (TaskEntity task : tasks) {
			try {
				// Additional check: atomic update of status only if it is still SCHEDULED
				// This is protection in case the status changed between selection and update
				int updated = taskRepository.updateStatusIfMatches(
						task.id(), 
						TaskStatus.SCHEDULED, 
						TaskStatus.ACTIVE
				);
				
				if (updated == 0) {
					log.warn("Task {} status was already changed, skipping", task.id());
					continue;
				}

				log.info("Activating task: {} (id: {}, traceId: {})", task.name(), task.id(), task.traceId());
				
				// Send task to Kafka
				kafkaProvider.sendMessage(new TaskMessage(task.id(), task.name(), task.traceId()));
				
				log.debug("[TASK_TRACE] Task activated and sent to Kafka: {}", task.traceId());
				
			} catch (Exception e) {
				log.error("Error processing task {}: {}", task.id(), e.getMessage(), e);
			}
		}
	}
}
