package com.wlodarczyk.dispatcher.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wlodarczyk.dispatcher.model.enums.AlertStatus;
import com.wlodarczyk.dispatcher.model.enums.AlertType;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AlertResponse(
        UUID id,
        AlertType type,
        AlertStatus status,
        String description,
        String sourceId,
        UUID unitId,
        Instant createdAt,
        Instant updatedAt,
        Instant resolvedAt
) {
}
