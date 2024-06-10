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
			"LEFT JOIN RoomBooking rb " +
			"ON r.hotel.id = rb.hotelId AND r.roomNumber = rb.roomNumber " +
			"WHERE r.hotel.id = :hotelId " +
			"AND r.type = :type " +
			"AND r.category = :category " +
			"AND (rb.checkInTime IS NULL OR rb.checkOutTime IS NULL OR rb.checkOutTime <= :checkInTime OR rb.checkInTime >= :checkOutTime)")
	List<Integer> findBySpecialFilters(String hotelId, String category, String type, String checkInTime, String checkOutTime);

}