package com.tripply.booking.service;

import com.tripply.booking.entity.Hotel;
import com.tripply.booking.repository.HotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel onboardHotel(Hotel hotel) {
        // Implement validation and business logic here
        return hotelRepository.save(hotel);
    }
}