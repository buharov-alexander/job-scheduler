package com.bukharov.scheduler.task_runner_service.repository;

import java.time.ZonedDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
@Entity
@Table(name="tasks")
public class TaskEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private TaskStatus status;

	@Column(name = "next_execution_time")
	private ZonedDateTime nextExecutionTime;
	
	@Column(name = "trace_id")
	private UUID traceId;

	/**
	 * Version for optimistic locking.
	 * Automatically increases on each update.
	 */
	@Version
	@Column(name = "version")
	private Long version;
}
