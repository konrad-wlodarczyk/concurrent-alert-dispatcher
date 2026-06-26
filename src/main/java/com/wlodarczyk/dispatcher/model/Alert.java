package com.wlodarczyk.dispatcher.model;

import com.wlodarczyk.dispatcher.model.enums.AlertPriority;
import com.wlodarczyk.dispatcher.model.enums.AlertStatus;
import com.wlodarczyk.dispatcher.model.enums.AlertType;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "alerts",
        indexes = {
                @Index(name = "idx_alerts_status", columnList = "status"),
                @Index(name = "idx_alerts_unit_id", columnList = "unit_id")

        })
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertPriority priority;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, length = 100)
    private String sourceId;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = true)
    private Unit unit;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = true)
    private Instant resolvedAt;

    protected Alert(){};

    public Alert(AlertType type, AlertPriority priority, String description, String sourceId){
        this.type = type;
        this.status = AlertStatus.PENDING;
        this.priority = priority;
        this.description = description;
        this.sourceId = sourceId;
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
