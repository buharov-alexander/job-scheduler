/***************       BEGIN-STANDARD-COPYRIGHT      ***************

 Copyright (c) 2009-2025, Spirent Communications.

 All rights reserved. Proprietary and confidential information of Spirent Communications.
 ***************        END-STANDARD-COPYRIGHT       ***************/
package com.bukharov.scheduler.execution_service.kafka;

import com.bukharov.scheduler.execution_service.repository.TaskEntity;
import com.bukharov.scheduler.execution_service.repository.TaskRepository;
import com.bukharov.scheduler.execution_service.repository.TaskStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import java.util.concurrent.TimeUnit;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TaskMessageListener {

	@Autowired
	private TaskRepository taskRepository;

	private final Counter taskFinishedCounter;
	private final Timer taskExecutionTimer;

	public TaskMessageListener(MeterRegistry meterRegistry) {
		this.taskFinishedCounter = meterRegistry.counter("job_scheduler_completed_tasks");
		this.taskExecutionTimer = meterRegistry.timer("job_scheduler_task_execution_time");
	};

	@Transactional
	@KafkaListener(topics = "${kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
	public void listener(TaskMessage task) throws InterruptedException {
		Thread.sleep(2000);
		log.debug(String.format("[TASK_TRACE] Task was executed: %s", task.getTraceId()));

		var taskEntityOpt = taskRepository.findById(task.getId());
		if (taskEntityOpt.isPresent()) {
			TaskEntity taskEntity = taskEntityOpt.get();
			taskEntity.status(TaskStatus.FINISHED);
			taskRepository.save(taskEntity);

			// Metrics: increment completed counter and record execution time
			try {
				taskFinishedCounter.increment();
				long durationMs = System.currentTimeMillis() - task.getStartedAt();
				taskExecutionTimer.record(durationMs, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				log.warn("Failed to record metrics for task {}: {}", task.getId(), e.getMessage());
			}
		} else {
			log.warn("Received task execution message for unknown task id={}", task.getId());
		}
	}
}
