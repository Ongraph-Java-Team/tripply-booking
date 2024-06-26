package com.tripply.booking.repository;

import com.tripply.booking.entity.Hotel;
import com.tripply.booking.model.response.HotelResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    @Query("SELECT new com.tripply.booking.model.response.HotelResponse(h.id, h.name, h.registrationNumber, h.description) FROM Hotel h")
    Page<HotelResponse> findAllByFilters(Pageable pageable);

}