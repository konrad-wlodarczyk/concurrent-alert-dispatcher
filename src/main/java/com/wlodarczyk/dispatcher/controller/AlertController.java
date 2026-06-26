package com.wlodarczyk.dispatcher.controller;

import com.wlodarczyk.dispatcher.dto.request.AlertRequest;
import com.wlodarczyk.dispatcher.dto.response.AlertResponse;
import com.wlodarczyk.dispatcher.service.AlertService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService){
        this.alertService = alertService;
    }

    @PostMapping
    public ResponseEntity<AlertResponse> createAlert(@Valid @RequestBody AlertRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alertService.createAlert(request));
    }

    @GetMapping
    public ResponseEntity<List<AlertResponse>> getAlerts(){
        return ResponseEntity.ok(alertService.getAlerts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertResponse> getAlertById(@PathVariable UUID id){
        return ResponseEntity.ok(alertService.getAlertById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlert(@PathVariable UUID id){
        alertService.deleteAlert(id);
        return ResponseEntity.noContent().build();
    }
}
