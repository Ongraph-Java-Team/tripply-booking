package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
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
    public ResponseEntity<ResponseModel<CountryCodeResponse>> addCountryCode(@RequestBody CountryCodeRequest lookupRequest) {
        log.info("Start Endpoint : adding countrycode : {}", lookupRequest);
        CountryCodeResponse response = lookupService.addCountryCode(lookupRequest);
        log.info("End Endpoint : countrycode added : {}", lookupRequest);
        ResponseModel<CountryCodeResponse> responseModel = ResponseModel.<CountryCodeResponse>builder()
                .data(response)
                .message("Country Code added successfully")
                .status(HttpStatus.CREATED)
                .build();
        return ResponseEntity.ok(responseModel);
    }

    @GetMapping("/country-code")
    public ResponseEntity<ResponseModel<List<CountryCodeResponse>>> getAllCountryCode() {
        log.info("Start Endpoint : getting all countrycode ");
        List<CountryCodeResponse> response = lookupService.getAllCountryCode();
        log.info("End Endpoint : List of countrycode ");
        ResponseModel<List<CountryCodeResponse>> responseModel = ResponseModel.<List<CountryCodeResponse>>builder()
                .data(response)
                .message("Country Codes retrieved successfully")
                .status(HttpStatus.FOUND)
                .build();
        return ResponseEntity.ok(responseModel);
    }
}
