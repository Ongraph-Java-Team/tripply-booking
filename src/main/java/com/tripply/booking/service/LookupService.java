package com.tripply.booking.service;

import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.request.StateRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.model.response.StateResponse;

import java.util.List;

public interface LookupService {
    CountryCodeResponse addCountryCode(CountryCodeRequest lookupRequest);

    List<CountryCodeResponse> getAllCountryCode();

    StateResponse addState(StateRequest request);
    List<StateResponse> getAllStateByCountryId(Long countryId);
}
