package com.bukharov.scheduler.task_runner_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskMessage {
	private Long id;
	private String name;
}
