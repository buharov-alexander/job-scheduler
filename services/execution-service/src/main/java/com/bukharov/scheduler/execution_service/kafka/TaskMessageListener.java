/***************       BEGIN-STANDARD-COPYRIGHT      ***************

 Copyright (c) 2009-2025, Spirent Communications.

 All rights reserved. Proprietary and confidential information of Spirent Communications.
 ***************        END-STANDARD-COPYRIGHT       ***************/
package com.bukharov.scheduler.execution_service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
public class TaskMessageListener {

	@KafkaListener(topics = "${kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
	public void listener(TaskMessage taskMessage) {
		log.info("Recieved message: {}", taskMessage);
	}
}
