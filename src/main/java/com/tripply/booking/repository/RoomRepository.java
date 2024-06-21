package com.tripply.booking.repository;

import com.tripply.booking.entity.Room;
import com.tripply.booking.entity.RoomBulkJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

	@Query("SELECT r.roomNumber FROM Room r WHERE r.hotel.id = :hotelId")
	List<Integer> findRoomNumbersByHotelId(UUID hotelId);
	Page<Room> findAll(Specification<RoomBulkJob> spec, Pageable pageable);

	@Query("SELECT r.roomNumber " +
			"FROM Room r " +
			"WHERE r.hotel.id = :hotelId " +
			"AND r.type = :type " +
			"AND r.category = :category ")
	List<Integer> findBySpecialFilters(UUID hotelId, String category, String type);
}