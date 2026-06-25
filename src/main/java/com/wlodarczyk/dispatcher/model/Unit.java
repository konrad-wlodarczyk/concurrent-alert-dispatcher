package com.wlodarczyk.dispatcher.model;

import com.wlodarczyk.dispatcher.model.enums.UnitStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "units",
        indexes = {
                @Index(name = "idx_units_status", columnList = "status"),
                @Index(name = "idx_units_call_sign", columnList = "callSign")
        })
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String callSign;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitStatus status;

    @OneToMany(mappedBy = "unit")
    private List<Alert> activeAlerts;

    @Column(nullable = false)
    private int maxConcurrentCalls;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    protected Unit(){};

    public Unit(String name, String callSign, int maxConcurrentCalls){
        this.name = name;
        this.callSign = callSign;
        this.maxConcurrentCalls = maxConcurrentCalls;
        this.status = UnitStatus.AVAILABLE;
        this.activeAlerts = new ArrayList<>();
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = Instant.now();
    }


}
