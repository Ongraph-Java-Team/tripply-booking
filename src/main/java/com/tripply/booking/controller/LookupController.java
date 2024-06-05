package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.request.CountryRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.model.response.CountryResponse;
import com.tripply.booking.service.LookupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lookup")
@Slf4j
public class LookupController {

    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @PostMapping("/country-code")
    public ResponseEntity<ResponseModel<CountryCodeResponse>> addCountryCode(@RequestBody CountryCodeRequest request) {
        log.info("Start Endpoint : adding countrycode : {}", request);
        CountryCodeResponse response = lookupService.addCountryCode(request);
        ResponseModel<CountryCodeResponse> responseModel = ResponseModel.<CountryCodeResponse>builder()
                .data(response)
                .message("Country Code added successfully")
                .status(HttpStatus.CREATED)
                .build();
        log.info("End Endpoint : countrycode added : {}", request);
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/country-code")
    public ResponseEntity<ResponseModel<List<CountryCodeResponse>>> getAllCountryCode() {
        log.info("Start Endpoint : getting all countrycode ");
        List<CountryCodeResponse> response = lookupService.getAllCountryCode();
        ResponseModel<List<CountryCodeResponse>> responseModel = ResponseModel.<List<CountryCodeResponse>>builder()
                .data(response)
                .message("Country Codes retrieved successfully")
                .status(HttpStatus.FOUND)
                .build();
        log.info("End Endpoint : List of countrycode ");
        return ResponseEntity.ok(responseModel);
    }

    @PostMapping("/countries")
    public ResponseEntity<ResponseModel<CountryResponse>> addCountry(@RequestBody CountryRequest request) {
        log.info("Start Endpoint : adding country : {}", request);
        CountryResponse response = lookupService.addCountry(request);
        ResponseModel<CountryResponse> responseModel = ResponseModel.<CountryResponse>builder()
                .data(response)
                .message("Country added successfully")
                .status(HttpStatus.CREATED)
                .build();
        log.info("End Endpoint : country added : {}", request);
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/countries")
    public ResponseEntity<ResponseModel<List<CountryResponse>>> getAllCountry() {
        log.info("Start Endpoint : getting all country ");
        List<CountryResponse> response = lookupService.getAllCountry();
        ResponseModel<List<CountryResponse>> responseModel = ResponseModel.<List<CountryResponse>>builder()
                .data(response)
                .message("Country retrieved successfully")
                .status(HttpStatus.FOUND)
                .build();
        log.info("End Endpoint : List of country ");
        return ResponseEntity.ok(responseModel);
    }
}
