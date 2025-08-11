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

@Slf4j
public class TaskMessageListener {

	@Autowired
	private TaskRepository taskRepository;

	@KafkaListener(topics = "${kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
	public void listener(TaskMessage task) {
		log.debug(String.format("[TASK_TRACE] Task was executed: %s", task.getTraceId()));

		var taskEntityOpt = taskRepository.findById(task.getId());
		if (taskEntityOpt.isPresent()) {
			TaskEntity taskEntity = taskEntityOpt.get();
			taskEntity.status(TaskStatus.FINISHED);
			taskRepository.save(taskEntity);
		}
	}
}
