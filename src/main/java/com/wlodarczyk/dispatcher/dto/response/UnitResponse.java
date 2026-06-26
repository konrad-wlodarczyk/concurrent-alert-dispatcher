package com.wlodarczyk.dispatcher.dto.response;

import com.wlodarczyk.dispatcher.model.enums.UnitStatus;

import java.time.Instant;
import java.util.UUID;

public record UnitResponse(
        UUID id,
        String name,
        String callSign,
        UnitStatus status,
        int activeAlertCount,
        int maxConcurrentCalls,
        Instant createdAt,
        Instant updatedAt
) {
}
