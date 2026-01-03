package com.bukharov.scheduler.execution_service.kafka;

import java.util.UUID;

import lombok.Data;

@Data
public class TaskMessage {
	private Long id;
	private String name;
	private UUID traceId;
	private long startedAt;
}
