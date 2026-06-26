package com.wlodarczyk.dispatcher.controller;

import com.wlodarczyk.dispatcher.dto.request.UnitRequest;
import com.wlodarczyk.dispatcher.dto.response.UnitResponse;
import com.wlodarczyk.dispatcher.service.UnitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService){
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseEntity<UnitResponse> createUnit(@Valid @RequestBody UnitRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(unitService.createUnit(request));
    }

    @GetMapping
    public  ResponseEntity<List<UnitResponse>> getUnits(){
        return ResponseEntity.ok(unitService.getUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitResponse> getUnitById(@PathVariable UUID id){
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnit(@PathVariable UUID id){
        unitService.deleteUnit(id);
        return ResponseEntity.noContent().build();
    }
}
