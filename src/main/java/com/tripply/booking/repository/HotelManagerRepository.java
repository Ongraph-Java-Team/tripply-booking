package com.tripply.booking.repository;

import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.HotelManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HotelManagerRepository extends JpaRepository<HotelManager, UUID>{

    List<HotelManager> findByHotel(Hotel hotel);

}
