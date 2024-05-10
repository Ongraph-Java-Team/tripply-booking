package com.tripply.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripply.booking.entity.Hotel;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.service.HotelService;



@RestController
@RequestMapping("/onboard")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping("/onboard-hotel")
    public ResponseEntity<ResponseModel<Hotel>> onboardHotel(@RequestBody Hotel hotel) {
        try {
            Hotel onboardedHotel = hotelService.onboardHotel(hotel);
            ResponseModel<Hotel> response = new ResponseModel<>(HttpStatus.OK, "Hotel onboarded successfully.", onboardedHotel);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseModel<Hotel> response = new ResponseModel<>(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred. Please try again later.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}