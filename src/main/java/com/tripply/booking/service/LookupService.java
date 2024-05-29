package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.response.CountryCodeResponse;

import java.util.List;

public interface LookupService {
    ResponseModel<CountryCodeResponse> addCountryCode(CountryCodeRequest lookupRequest);

    ResponseModel<List<CountryCodeResponse>> getAllCountryCode();
}
