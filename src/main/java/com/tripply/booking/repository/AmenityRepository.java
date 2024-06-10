package com.tripply.booking.repository;

import com.tripply.booking.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    boolean existsByAmenityName(String amenityName);
    Set<Amenity> findByIdIn(List<Long> amenityIds);
}
