package com.wlodarczyk.dispatcher.mapper;

import com.wlodarczyk.dispatcher.dto.request.UnitRequest;
import com.wlodarczyk.dispatcher.dto.response.UnitResponse;
import com.wlodarczyk.dispatcher.model.Unit;
import org.springframework.stereotype.Component;

@Component
public class UnitMapper {

    public Unit toEntity(UnitRequest request){
        return new Unit(
                request.name(),
                request.callSign(),
                request.maxConcurrentCalls()
        );
    }

    public UnitResponse toResponse(Unit unit){
        return new UnitResponse(
                unit.getId(),
                unit.getName(),
                unit.getCallSign(),
                unit.getStatus(),
                unit.getActiveAlerts().size(),
                unit.getMaxConcurrentCalls(),
                unit.getCreatedAt(),
                unit.getUpdatedAt()
        );
    }
}
