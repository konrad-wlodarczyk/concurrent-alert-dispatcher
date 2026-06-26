package com.wlodarczyk.dispatcher.service;

import com.wlodarczyk.dispatcher.dto.request.UnitRequest;
import com.wlodarczyk.dispatcher.dto.response.UnitResponse;
import com.wlodarczyk.dispatcher.mapper.UnitMapper;
import com.wlodarczyk.dispatcher.model.Unit;
import com.wlodarczyk.dispatcher.model.enums.UnitStatus;
import com.wlodarczyk.dispatcher.repository.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    public UnitService(UnitRepository unitRepository, UnitMapper unitMapper){
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    @Transactional
    public UnitResponse createUnit(UnitRequest request){
        Unit unit = unitMapper.toEntity(request);
        return unitMapper.toResponse(unitRepository.save(unit));
    }

    @Transactional
    public List<UnitResponse> getUnits(){
        return unitRepository.findAll().stream()
                .map(unitMapper::toResponse)
                .toList();
    }

    @Transactional
    public UnitResponse getUnitById(UUID id){
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unit with ID: " + id + " does not exist"));

        return unitMapper.toResponse(unit);
    }

    @Transactional
    public void deleteUnit(UUID id){
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unit with ID: " + id + " does not exist"));

        if (!unit.getActiveAlerts().isEmpty() || unit.getStatus() == UnitStatus.BUSY) {
            throw new IllegalStateException("Cannot delete a unit that is currently on duty, busy, or has active alerts.");
        }

        unitRepository.delete(unit);
    }
}
