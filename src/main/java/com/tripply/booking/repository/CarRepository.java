package com.tripply.booking.repository;

import com.tripply.booking.entity.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<CarDetails, Integer> {

    Optional<CarDetails> getCarDetailsByRegistrationNo(String registrationNo);

}
