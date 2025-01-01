package com.bukharov.scheduler.execution_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class TaskMessage {
	private Long id;
	private String name;
}
