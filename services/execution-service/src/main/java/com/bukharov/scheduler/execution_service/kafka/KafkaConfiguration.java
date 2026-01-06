package com.bukharov.scheduler.execution_service.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfiguration {

	@Value("${kafka.address}")
	private String kafkaAddress;

	public ConsumerFactory<Long, TaskMessage> consumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "task_consuming");

		JsonDeserializer<TaskMessage> jsonDeserializer = new JsonDeserializer<>(TaskMessage.class);
		jsonDeserializer.addTrustedPackages("*");
		jsonDeserializer.setUseTypeHeaders(false);
		return new DefaultKafkaConsumerFactory<>(config, new LongDeserializer(), jsonDeserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<Long, TaskMessage> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Long, TaskMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
