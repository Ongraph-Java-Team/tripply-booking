package com.tripply.booking.repository;

import com.tripply.booking.entity.RoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomBookingRepository extends JpaRepository<RoomBooking, UUID> {

    @Query("SELECT rb.roomNumbers " +
            "FROM RoomBooking rb " +
            "WHERE rb.hotelId = :hotelId " +
            "AND ((rb.checkInTime < :checkOutTime AND rb.checkOutTime > :checkInTime) " +
            "OR (rb.checkInTime >= :checkInTime AND rb.checkInTime < :checkOutTime) " +
            "OR (rb.checkOutTime > :checkInTime AND rb.checkOutTime <= :checkOutTime))")
    List<List<Integer>> findBookedRooms(UUID hotelId, LocalDateTime checkInTime, LocalDateTime checkOutTime);
}
