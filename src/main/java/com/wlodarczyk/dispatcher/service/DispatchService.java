package com.wlodarczyk.dispatcher.service;

import com.wlodarczyk.dispatcher.model.Alert;
import com.wlodarczyk.dispatcher.model.Unit;
import com.wlodarczyk.dispatcher.model.enums.AlertStatus;
import com.wlodarczyk.dispatcher.model.enums.AlertType;
import com.wlodarczyk.dispatcher.model.enums.UnitType;
import com.wlodarczyk.dispatcher.repository.AlertRepository;
import com.wlodarczyk.dispatcher.repository.UnitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DispatchService {

    private final UnitRepository unitRepository;
    private final AlertRepository alertRepository;

    public DispatchService(UnitRepository unitRepository, AlertRepository alertRepository){
        this.unitRepository = unitRepository;
        this.alertRepository = alertRepository;
    }

    @Transactional
    public void dispatch(Alert alert){
        Alert managedAlert = alertRepository.findById(alert.getId()).orElseThrow();

        UnitType required = resolveUnitType(managedAlert.getType());
        List<Unit> available = unitRepository.findAvailableUnitsByType(required);

        if (available.isEmpty()){
            managedAlert.markFailed();
            return;
        }

        Unit unit = unitRepository.findById(available.get(0).getId()).orElseThrow();
        managedAlert.assignUnit(unit);
        unit.assignAlert(alert);
        managedAlert.markDispatched();
    }

    @Transactional
    public void resolve(UUID id){
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alert not found: " + id));

        if(alert.getStatus() != AlertStatus.DISPATCHED) {
            throw new IllegalStateException("Only DISPATCHED alerts can be resolved");
        }

        Unit unit = alert.getUnit();

        unit.releaseAlert(alert);
        alert.markResolved();
    }

    private UnitType resolveUnitType(AlertType alertType) {
        return switch (alertType) {
            case MEDICAL -> UnitType.MEDICAL;
            case FIRE -> UnitType.FIRE;
            case POLICE -> UnitType.POLICE;
        };
    }
}
