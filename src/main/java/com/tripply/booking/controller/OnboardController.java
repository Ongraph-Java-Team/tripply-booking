package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.response.HotelResponse;
import com.tripply.booking.service.OnboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OnboardController {

    @Autowired
    private OnboardService onboardService;

    @PostMapping("/onboard-hotel")
    public ResponseEntity<ResponseModel<HotelResponse>> onboardHotel(@Validated @RequestBody HotelRequest hotelRequest) {
        log.info("Start Endpoint: /onboard-hotel onboardHotel() with userId: {}", hotelRequest.getManagerDetails().getUserId());
        ResponseModel<HotelResponse> response = onboardService.onboardHotel(hotelRequest);
        log.info("End Endpoint: /onboard-hotel onboardHotel() with userId: {}", hotelRequest.getManagerDetails().getUserId());
        return ResponseEntity.ok(response);
    }

}
