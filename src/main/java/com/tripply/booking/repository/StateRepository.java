package com.tripply.booking.repository;

import com.tripply.booking.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {

    State getStateByStateName(String stateName);

    List<State> findAllByCountryId(Long countryId);
}
