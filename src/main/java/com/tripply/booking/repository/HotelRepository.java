package com.tripply.booking.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripply.booking.entity.Hotel;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {
   
}


