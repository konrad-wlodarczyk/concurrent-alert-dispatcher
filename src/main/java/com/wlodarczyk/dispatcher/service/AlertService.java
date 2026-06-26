package com.wlodarczyk.dispatcher.service;

import com.wlodarczyk.dispatcher.dto.request.AlertRequest;
import com.wlodarczyk.dispatcher.dto.response.AlertResponse;
import com.wlodarczyk.dispatcher.mapper.AlertMapper;
import com.wlodarczyk.dispatcher.model.Alert;
import com.wlodarczyk.dispatcher.model.enums.AlertStatus;
import com.wlodarczyk.dispatcher.repository.AlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AlertService {

    private final AlertMapper alertMapper;
    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository, AlertMapper alertMapper){
        this.alertMapper = alertMapper;
        this.alertRepository = alertRepository;
    }

    @Transactional
    public AlertResponse createAlert(AlertRequest request){
        Alert alert = alertMapper.toEntity(request);

        return alertMapper.toResponse(alertRepository.save(alert));
    }

    @Transactional
    public List<AlertResponse> getAlerts(){
        return alertRepository.findAll().stream()
                .map(alertMapper::toResponse)
                .toList();
    }

    @Transactional
    public AlertResponse getAlertById(UUID id){
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alert with ID: " + id + " does not exist"));

        return alertMapper.toResponse(alert);
    }

    @Transactional
    public void deleteAlert(UUID id){
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alert with ID: " + id + " does not exist"));

        if(alert.getStatus() != AlertStatus.RESOLVED && alert.getStatus() != AlertStatus.FAILED){
            throw new IllegalStateException("Cannot delete alerts that are not either resolved or failed");
        }

        alertRepository.delete(alert);
    }

}
