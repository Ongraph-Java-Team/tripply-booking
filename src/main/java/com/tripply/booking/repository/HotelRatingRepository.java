package com.tripply.booking.repository;

import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.HotelRating;
import com.tripply.booking.entity.UserProfile;
import com.tripply.booking.model.TopRatedHotelProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HotelRatingRepository extends JpaRepository<HotelRating, UUID>{

    boolean existsByHotelAndUserId(Hotel hotel, UUID userId);
    Page<HotelRating> findAll(Specification<HotelRating> specification, Pageable pageable);
    @Query("SELECT hr.hotel.id AS hotelId, h.name AS hotelName, AVG(hr.rating) AS averageRating " +
            "FROM HotelRating hr JOIN hr.hotel h " +
            "GROUP BY hr.hotel.id, h.name " +
            "ORDER BY averageRating DESC")
    Page<TopRatedHotelProjection> findTopRatedHotels(Pageable pageable);

}
