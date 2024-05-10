package com.tripply.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tripply.booking.model.request.FlightRequest;

@Repository
public interface FlightRepository extends JpaRepository<FlightRequest, Long>{

}
