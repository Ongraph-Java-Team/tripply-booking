package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.entity.Country;
import com.tripply.booking.entity.CountryCode;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.request.CountryRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.model.response.CountryResponse;
import com.tripply.booking.repository.CountryCodeRepository;
import com.tripply.booking.repository.CountryRepository;
import com.tripply.booking.service.LookupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LookupServiceImpl implements LookupService {

    private final CountryCodeRepository countryCodeRepository;

    private final CountryRepository countryRepository;

    public LookupServiceImpl(CountryCodeRepository countryCodeRepository, CountryRepository countryRepository) {
        this.countryCodeRepository = countryCodeRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public CountryCodeResponse addCountryCode(CountryCodeRequest request) {
        Optional<CountryCode> countryCodeOptional = countryCodeRepository.getCountryCodeByCode(request.getCode());
        if (countryCodeOptional.isPresent()) {
            throw new BadRequestException("Country Code already Added.");
        }
        CountryCode countryCode = setCountryCode(request);
        countryCode = countryCodeRepository.save(countryCode);
        CountryCodeResponse countryCodeResponse = setCountryCodeToResponse(countryCode);
        return countryCodeResponse;
    }

    @Override
    public List<CountryCodeResponse> getAllCountryCode() {
        List<CountryCode> countryCodeList = countryCodeRepository.findAll();
        List<CountryCodeResponse> countryCodeResponseList = countryCodeList.stream()
                .map(this::setCountryCodeToResponse)
                .collect(Collectors.toList());
        return countryCodeResponseList;
    }

    @Override
    public CountryResponse addCountry(CountryRequest request) {
        Optional<Country> countryOptional = countryRepository.getByCountryName(request.getCountryName());
        if (countryOptional.isPresent()) {
            throw new BadRequestException("Country is already Added.");
        }
        Country country = Country.builder().countryName(request.getCountryName()).build();
        country = countryRepository.save(country);
        CountryResponse countryResponse = CountryResponse.builder()
                .countryName(country.getCountryName())
                .id(country.getId())
                .build();
        return countryResponse;
    }

    @Override
    public List<CountryResponse> getAllCountry() {
        List<Country> countryList = countryRepository.findAll();
        List<CountryResponse> response = countryList.stream()
                .map(country ->CountryResponse.builder()
                        .countryName(country.getCountryName())
                        .id(country.getId())
                        .build())
                .collect(Collectors.toList());
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
