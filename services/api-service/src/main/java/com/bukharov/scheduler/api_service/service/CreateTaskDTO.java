package com.bukharov.scheduler.api_service.service;

import java.time.ZonedDateTime;

public record CreateTaskDTO(String name, ZonedDateTime executionTime) {
}
