package com.tripply.booking.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tripply.booking.dto.HotelDTO;
import com.tripply.booking.service.HotelService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    @Autowired
    HotelService hotelService;

    @GetMapping
    public List<HotelDTO> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable Long id) {
        Optional<HotelDTO> hotel = hotelService.getHotelById(id);
        if (hotel.isPresent()) {
            return ResponseEntity.ok(hotel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<HotelDTO> addHotel(@RequestBody HotelDTO hotel) {
        return new ResponseEntity<>(hotelService.addHotel(hotel), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHotel(@PathVariable Long id, @RequestBody HotelDTO hotel) {
        Optional<HotelDTO> existingHotel = hotelService.getHotelById(id);
        if (existingHotel.isPresent()) {
            HotelDTO updatedHotel = hotelService.updateHotel(id, hotel);
            return ResponseEntity.ok(updatedHotel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        Optional<HotelDTO> hotel = hotelService.getHotelById(id);
        if (hotel.isPresent()) {
            hotelService.deleteHotel(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/onboard-hotel")
    public ResponseEntity<?> onboardHotel(@RequestBody HotelDTO hotelRequest) {
        HotelDTO onboardedHotel = hotelService.onboardHotel(hotelRequest);
        return ResponseEntity.ok(onboardedHotel);
    }
}
