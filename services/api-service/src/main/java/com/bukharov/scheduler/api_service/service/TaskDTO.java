package com.bukharov.scheduler.api_service.service;

import java.time.ZonedDateTime;

public record TaskDTO(String name, ZonedDateTime executionTime) {
}
