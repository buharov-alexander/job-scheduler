package com.bukharov.scheduler.task_runner_service.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

	List<TaskEntity> findByNextExecutionTimeLessThan(ZonedDateTime time);
}
