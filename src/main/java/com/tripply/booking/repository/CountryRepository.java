package com.tripply.booking.repository;

import com.tripply.booking.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> getByCountryName(String countryName);
}
