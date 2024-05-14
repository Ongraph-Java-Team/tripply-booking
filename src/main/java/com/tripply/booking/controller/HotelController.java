package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.response.HotelResponse;
import com.tripply.booking.model.response.InviteResponse;
import com.tripply.booking.service.HotelService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hotels")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    public ResponseEntity<ResponseModel<InviteResponse>> addHotel(@Valid @RequestBody HotelRequest hotelRequest) {
        log.info("Start Endpoint: / addHotel(): {}", hotelRequest.getName());
        ResponseModel<InviteResponse> response = hotelService.createHotel(hotelRequest);
        log.info("End Endpoint: / addHotel(): {}", hotelRequest.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<HotelResponse>> getHotelById(@PathVariable("id") UUID hotelId) {
        log.info("Start Endpoint: /{id} getHotelById() with id: {}", hotelId);
        ResponseModel<HotelResponse> response = hotelService.getHotelById(hotelId);
        log.info("End Endpoint: /{id} getHotelById() with id: {}", hotelId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<HotelResponse>> updateHotelDetails(@PathVariable("id") UUID hotelId,
                                                                           @Validated @RequestBody HotelRequest updateHotelRequest) {
        log.info("Start Endpoint: /{id} updateHotelDetails() with id: {}", hotelId);
        ResponseModel<HotelResponse> response = hotelService.updateHotelDetails(hotelId, updateHotelRequest);
        log.info("End Endpoint: /{id} updateHotelDetails() with id: {}", hotelId);
        return ResponseEntity.ok(response);
    }

}
