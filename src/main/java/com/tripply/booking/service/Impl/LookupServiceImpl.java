package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.entity.CountryCode;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.repository.LookupRepository;
import com.tripply.booking.service.LookupService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LookupServiceImpl implements LookupService {

    private final LookupRepository lookupRepository;

    public LookupServiceImpl(LookupRepository lookupRepository) {
        this.lookupRepository = lookupRepository;
    }

    @Override
    public ResponseModel<CountryCodeResponse> addCountryCode(CountryCodeRequest request) {
        Optional<CountryCode> countryCodeOptional = lookupRepository.getCountryCodeByCode(request.getCode());
        if (countryCodeOptional.isPresent()) {
            throw new BadRequestException("Country Code already Added.");
        }
        CountryCode countryCode = setCountryCode(request);
        countryCode = lookupRepository.save(countryCode);
        CountryCodeResponse countryCodeResponse = setCountryCodeToResponse(countryCode);
        ResponseModel<CountryCodeResponse> response = new ResponseModel<>();
        response.setMessage("Country Code added successfully");
        response.setStatus(HttpStatus.CREATED);
        response.setData(countryCodeResponse);
        return response;
    }

    @Override
    public ResponseModel<List<CountryCodeResponse>> getAllCountryCode() {
        List<CountryCode> countryCodeList = lookupRepository.findAll();
        List<CountryCodeResponse> countryCodeResponseList = countryCodeList.stream()
                .map(this::setCountryCodeToResponse)
                .collect(Collectors.toList());
        ResponseModel<List<CountryCodeResponse>> response = new ResponseModel<>();
        response.setMessage("Country Codes retrieved successfully");
        response.setStatus(HttpStatus.FOUND);
        response.setData(countryCodeResponseList);
        return response;
    }

    private CountryCode setCountryCode(CountryCodeRequest request) {
        CountryCode countryCode = new CountryCode();
        countryCode.setDialCode(request.getDialCode());
        countryCode.setCode(request.getCode());
        countryCode.setName(request.getName());
        return countryCode;
    }

    private CountryCodeResponse setCountryCodeToResponse(CountryCode countryCode) {
        return CountryCodeResponse.builder()
                .code(countryCode.getCode())
                .id(countryCode.getId())
                .name(countryCode.getName())
                .dialCode(countryCode.getDialCode())
                .build();
    }

}
