package com.tripply.booking.controller;

import com.tripply.booking.model.request.AmentiesRequest;
import com.tripply.booking.model.response.AmenityResponse;
import com.tripply.booking.model.response.HotelResponse;
import com.tripply.booking.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.response.InviteResponse;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@RestController
@Slf4j
public class AmenityController {

    @Autowired
    private AmenityService amenityService;
	
	@PostMapping("/add-amenity")
    public ResponseEntity<ResponseModel<AmenityResponse>> addAmenity(@RequestBody AmentiesRequest amentiesRequest) {
        log.info("Start Endpoint: / addAmenity(): {}", amentiesRequest.getAmenityName());
        ResponseModel<AmenityResponse> response = amenityService.addAmenity(amentiesRequest);
        log.info("End Endpoint: / addAmenity(): {}", amentiesRequest.getAmenityName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-amenity/{id}")
    public ResponseEntity<ResponseModel<AmenityResponse>> getAmenity(@PathVariable("id") Long Id) {
        log.info("Start Endpoint: /{id} getAmenity() with id: {}", Id);
        ResponseModel<AmenityResponse> response = amenityService.getAmenity(Id);
        log.info("End Endpoint: /{id} getAmenity() with id: {}",Id);
        return ResponseEntity.ok(response);
    }
}
