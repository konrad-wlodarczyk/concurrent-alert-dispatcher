package com.wlodarczyk.dispatcher.mapper;

import com.wlodarczyk.dispatcher.dto.request.AlertRequest;
import com.wlodarczyk.dispatcher.dto.response.AlertResponse;
import com.wlodarczyk.dispatcher.model.Alert;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {

    public Alert toEntity(AlertRequest request){
        return new Alert(
            request.type(),
            request.priority(),
            request.description(),
            request.sourceId()
        );
    }

    public AlertResponse toResponse(Alert alert){
        return new AlertResponse(
                alert.getId(),
                alert.getType(),
                alert.getStatus(),
                alert.getDescription(),
                alert.getSourceId(),
                alert.getUnitId(),
                alert.getCreatedAt(),
                alert.getUpdatedAt(),
                alert.getResolvedAt()
        );
    }
}
