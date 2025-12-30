package com.bukharov.scheduler.api_service.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

    Collection<TaskEntity> findByStatus(TaskStatus status);
}
