package com.wlodarczyk.dispatcher.model;

import com.wlodarczyk.dispatcher.model.enums.UnitStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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
@SQLDelete(sql = "UPDATE units SET deleted = true, updated_at = NOW() WHERE id = ?")
@SQLRestriction("deleted = false")
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

    @Column(nullable = false)
    private boolean deleted;

    protected Unit(){};

    public Unit(String name, String callSign, int maxConcurrentCalls){
        this.name = name;
        this.callSign = callSign;
        this.maxConcurrentCalls = maxConcurrentCalls;
        this.status = UnitStatus.AVAILABLE;
        this.activeAlerts = new ArrayList<>();
        this.deleted = false;
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

    public void setDeleted(boolean deleted){this.deleted = deleted;}

    public UUID getId(){return this.id;}
    public String getName(){return this.name;}
    public String getCallSign(){return this.callSign;}
    public UnitStatus getStatus(){return this.status;}
    public List<Alert> getActiveAlerts(){return this.activeAlerts;}
    public int getMaxConcurrentCalls(){return this.maxConcurrentCalls;}
    public Instant getCreatedAt(){return this.createdAt;}
    public Instant getUpdatedAt(){return this.updatedAt;}
    public boolean isDeleted(){return this.deleted;}

}
