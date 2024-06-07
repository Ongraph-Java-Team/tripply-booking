package com.tripply.booking.service;

import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.request.StateRequest;
import com.tripply.booking.model.request.CountryRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.model.response.StateResponse;
import com.tripply.booking.model.response.CountryResponse;

import java.util.List;

public interface LookupService {

    CountryCodeResponse addCountryCode(CountryCodeRequest request);
    List<CountryCodeResponse> getAllCountryCode();

    CountryResponse addCountry(CountryRequest request);
    List<CountryResponse> getAllCountry();

    StateResponse addState(StateRequest request);
    List<StateResponse> getAllStateByCountryId(Long countryId);
}
