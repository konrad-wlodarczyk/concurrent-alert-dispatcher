package com.wlodarczyk.dispatcher.dto.request;

import com.wlodarczyk.dispatcher.model.enums.AlertPriority;
import com.wlodarczyk.dispatcher.model.enums.AlertType;
import com.wlodarczyk.dispatcher.model.enums.UnitStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UnitRequest(
        @NotBlank(message = "Unit name cannot be blank")
        @Size(max = 50)
        String name,

        @NotBlank(message = "Call sign cannot be blank")
        @Size(max = 50)
        String callSign,

        @Min(value = 1, message = "Max concurrent calls must be at least 1")
        int maxConcurrentCalls
) {}
