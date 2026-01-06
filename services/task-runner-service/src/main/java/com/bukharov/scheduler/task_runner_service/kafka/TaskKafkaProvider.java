package com.bukharov.scheduler.task_runner_service.kafka;

import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class TaskKafkaProvider {

	@Value("${kafka.topic.name}")
	private String kafkaTopicName;
	private KafkaTemplate<Long, TaskMessage> kafkaTemplate;

	public TaskKafkaProvider(KafkaTemplate<Long, TaskMessage> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(TaskMessage taskMessage) {
		Long key = taskMessage.getId() % 2; // Simple partitioning key based on task ID
		CompletableFuture<SendResult<Long, TaskMessage>> future = kafkaTemplate.send(kafkaTopicName, key, taskMessage);

		future
				.thenAccept(result -> {
					log.debug(String.format("[TASK_TRACE] Task was placed to kafka: %s", taskMessage.getTraceId()));
				})
				.exceptionally(ex -> {
					log.error("Unable to send message = {} dut to: {}", taskMessage, ex.getMessage());
					return null;
				});
	}
}
