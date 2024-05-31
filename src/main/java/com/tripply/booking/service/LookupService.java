package com.tripply.booking.service;

import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.response.CountryCodeResponse;

import java.util.List;

public interface LookupService {
    CountryCodeResponse addCountryCode(CountryCodeRequest lookupRequest);

    List<CountryCodeResponse> getAllCountryCode();
}
