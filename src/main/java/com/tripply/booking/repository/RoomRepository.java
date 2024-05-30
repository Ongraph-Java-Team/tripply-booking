package com.tripply.booking.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tripply.booking.entity.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    boolean existsByHotelIdAndRoomNumber(UUID hotelId, String roomNumber);
}