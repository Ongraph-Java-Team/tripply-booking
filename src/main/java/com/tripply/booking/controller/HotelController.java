package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.response.InviteResponse;
import com.tripply.booking.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<ResponseModel<InviteResponse>> addHotel(@Validated @RequestBody HotelRequest hotelRequest) {
        log.info("Start Endpoint: / addHotel(): {}", hotelRequest.getName());
        ResponseModel<InviteResponse> response = hotelService.createHotel(hotelRequest);
        log.info("End Endpoint: / addHotel(): {}", hotelRequest.getName());
        return ResponseEntity.ok(response);
    }

}
