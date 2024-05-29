package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.service.LookupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lookup")
@Slf4j
public class LookupController {

    @Autowired
    LookupService lookupService;

    @PostMapping("/country-code")
    public ResponseEntity<ResponseModel<CountryCodeResponse>> addCountryCode(@RequestBody CountryCodeRequest lookupRequest) {
        log.info("Start Endpoint : adding countrycode : {}", lookupRequest);
        ResponseModel<CountryCodeResponse> response = lookupService.addCountryCode(lookupRequest);
        log.info("End Endpoint : countrycode added : {}", lookupRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/country-code")
    public ResponseEntity<ResponseModel<List<CountryCodeResponse>>> getAllCountryCode() {
        log.info("Start Endpoint : getting all countrycode ");
        ResponseModel<List<CountryCodeResponse>> response = lookupService.getAllCountryCode();
        log.info("End Endpoint : List of countrycode ");
        return ResponseEntity.ok(response);
    }
}
