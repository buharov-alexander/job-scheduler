package com.bukharov.scheduler.api_service.api;

import java.time.ZonedDateTime;

record TaskDTO(String name, ZonedDateTime executionTime) {
}
