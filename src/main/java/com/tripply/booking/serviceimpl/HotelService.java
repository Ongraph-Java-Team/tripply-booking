package com.tripply.booking.serviceimpl;

import com.tripply.booking.dto.HotelDTO;

import java.util.List;
import java.util.Optional;

public interface HotelService {
    List<HotelDTO> getAllHotels();

    Optional<HotelDTO> getHotelById(Long id);

    HotelDTO addHotel(HotelDTO hotel);

    HotelDTO updateHotel(Long id, HotelDTO updatedHotel);

    void deleteHotel(Long id);
    
    HotelDTO onboardHotel(HotelDTO hotelRequest);
}
