package com.tripply.booking.repository;

import com.tripply.booking.entity.CountryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryCodeRepository extends JpaRepository<CountryCode, Long> {

    Optional<CountryCode> getCountryCodeByCode(String code);
}
