package com.tripply.booking.repository;

import com.tripply.booking.model.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<CarDetails, String> {
}
