package com.tripply.booking.repository;

import com.tripply.booking.entity.RoomBookingStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomBookingStageRepository extends JpaRepository<RoomBookingStage, UUID> {
    List<RoomBookingStage> findAllByIsActive(boolean isActive);
}
