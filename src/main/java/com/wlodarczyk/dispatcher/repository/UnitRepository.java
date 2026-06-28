package com.wlodarczyk.dispatcher.repository;

import com.wlodarczyk.dispatcher.model.Unit;
import com.wlodarczyk.dispatcher.model.enums.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UnitRepository extends JpaRepository<Unit, UUID> {

    @Query("SELECT u FROM Unit u WHERE u.type = :type AND u.status != 'OFFLINE' AND SIZE(u.activeAlerts) < u.maxConcurrentCalls ORDER BY SIZE(u.activeAlerts) ASC")
    List<Unit> findAvailableUnitsByType(@Param("type") UnitType type);

}
