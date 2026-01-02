package com.bukharov.scheduler.task_runner_service.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {

	List<TaskEntity> findByNextExecutionTimeLessThanAndStatus(ZonedDateTime time, TaskStatus status);

	/**
	 * Atomic selection and locking of tasks for execution.
	 * Uses SELECT FOR UPDATE SKIP LOCKED to prevent race condition
	 * when multiple instances of the service are running.
	 * SKIP LOCKED guarantees that if a task is already locked by another instance,
	 * it will be skipped and each instance will get a different task.
	 * 
	 * Uses native query to guarantee support for SKIP LOCKED in PostgreSQL.
	 * 
	 * @param time current time
	 * @param status task status for selection
	 * @return list of locked tasks
	 */
	@Query(value = "SELECT * FROM tasks WHERE next_execution_time < :time " +
			"AND status = :status " +
			"ORDER BY next_execution_time ASC " +
			"LIMIT 100 " +
			"FOR UPDATE SKIP LOCKED" , nativeQuery = true)
	List<TaskEntity> findAndLockTasksForExecution(
			@Param("time") ZonedDateTime time,
			@Param("status") String status
	);

	/**
	 * Atomic update of task status only if it is still in the specified status.
	 * Used to prevent processing the same task by multiple instances.
	 * 
	 * @param taskId task ID
	 * @param oldStatus current status (must match)
	 * @param newStatus new status
	 * @return number of updated rows (1 if successful, 0 if status already changed)
	 */
	@Modifying
	@Transactional
	@Query("UPDATE TaskEntity t SET t.status = :newStatus WHERE t.id = :taskId AND t.status = :oldStatus")
	int updateStatusIfMatches(@Param("taskId") Long taskId, @Param("oldStatus") TaskStatus oldStatus, @Param("newStatus") TaskStatus newStatus);
}
