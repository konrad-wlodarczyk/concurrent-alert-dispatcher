package com.wlodarczyk.dispatcher.dto.request;

import com.wlodarczyk.dispatcher.model.enums.AlertPriority;
import com.wlodarczyk.dispatcher.model.enums.AlertType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlertRequest(
        @NotNull(message = "Alert type cannot be blank")
        AlertType type,

        @NotNull(message = "Alert priority cannot be blank")
        AlertPriority priority,

        @Size(max = 255)
        String description,

        @NotBlank(message = "Source ID cannot be blank")
        @Size(max = 100)
        String sourceId
) {
}
