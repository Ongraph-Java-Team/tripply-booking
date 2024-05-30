package com.tripply.booking.repository;

import com.tripply.booking.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    boolean existsByAmenityName(String amenityName);
}
