package com.bukharov.scheduler.task_runner_service.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
@ConditionalOnProperty(
	name = "spring.kafka.admin.enabled",
	havingValue = "true"
)
public class KafkaTopicConfiguration {

	@Value("${kafka.address}")
	private String kafkaAddress;
	@Value("${kafka.topic.name}")
	private String kafkaTopicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> config = new HashMap<>();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);

        return new KafkaAdmin(config);
    }

	@Bean
	@ConditionalOnProperty(
		name = "spring.kafka.admin.enabled",
		havingValue = "true"
	)
    public NewTopic topic() {
        return new NewTopic(kafkaTopicName, 2, (short) 1);
    }
}
