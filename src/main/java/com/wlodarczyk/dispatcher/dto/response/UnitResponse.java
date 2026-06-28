package com.wlodarczyk.dispatcher.dto.response;

import com.wlodarczyk.dispatcher.model.enums.UnitStatus;
import com.wlodarczyk.dispatcher.model.enums.UnitType;

import java.time.Instant;
import java.util.UUID;

public record UnitResponse(
        UUID id,
        String name,
        String callSign,
        UnitType type,
        UnitStatus status,
        int activeAlertCount,
        int maxConcurrentCalls,
        Instant createdAt,
        Instant updatedAt
) {
}
