package com.tripply.booking.repository;

import com.tripply.booking.entity.CarDetails;
import com.tripply.booking.model.request.CarRequest;
import com.tripply.booking.model.response.CarResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<CarDetails, Integer> {
    Optional<CarDetails> getCarDetailsByRegistrationNo(String registrationNo);

}
