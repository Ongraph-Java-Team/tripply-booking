package com.tripply.booking.repository;

import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.HotelRating;
import com.tripply.booking.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HotelRatingRepository extends JpaRepository<HotelRating, UUID>{

    boolean existsByHotelAndUserId(Hotel hotel, UUID userId);

}
