package com.tripply.booking.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripply.booking.dto.HotelDTO;

@Repository
public interface HotelRepository extends JpaRepository<HotelDTO, Long> {
    // Your repository methods
}


