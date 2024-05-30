package com.tripply.booking.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tripply.booking.entity.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
	@Query("SELECT r.roomNumber FROM Room r WHERE r.hotel.id = :hotelId")
	List<Integer> findRoomNumbersByHotelId(UUID hotelId);
}