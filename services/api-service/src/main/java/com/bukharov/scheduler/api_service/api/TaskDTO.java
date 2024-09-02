package com.bukharov.scheduler.api_service.api;

import java.time.ZonedDateTime;

public record TaskDTO(int id, String name, ZonedDateTime start) {
}
