package com.tripply.booking.serviceimpl;

import com.tripply.booking.dto.HotelDTO;
import com.tripply.booking.repository.HotelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    HotelRepository hotelRepository;

    @Override
    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Optional<HotelDTO> getHotelById(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public HotelDTO addHotel(HotelDTO hotel) {
        return hotelRepository.save(hotel);
    }
    
    @Override
    public HotelDTO onboardHotel(HotelDTO hotelRequest) {
        HotelDTO hotelDTO = convertToHotelDTO(hotelRequest);
        return hotelRepository.save(hotelDTO);
    }

    @Override
    public HotelDTO updateHotel(Long id, HotelDTO updatedHotel) {
        Optional<HotelDTO> existingHotelOptional = hotelRepository.findById(id);
        if (existingHotelOptional.isPresent()) {
            HotelDTO existingHotel = existingHotelOptional.get();
            existingHotel.setName(updatedHotel.getName());
            existingHotel.setAddress(updatedHotel.getAddress());
            existingHotel.setCity(updatedHotel.getCity());
            existingHotel.setState(updatedHotel.getState());
            existingHotel.setCountry(updatedHotel.getCountry());
            existingHotel.setDescription(updatedHotel.getDescription());
            existingHotel.setPhoneNumber(updatedHotel.getPhoneNumber());
            existingHotel.setEmail(updatedHotel.getEmail());
            existingHotel.setWebsite(updatedHotel.getWebsite());
            existingHotel.setAmenities(updatedHotel.getAmenities());
            existingHotel.setAdminRequest(updatedHotel.getAdminRequest());
            return hotelRepository.save(existingHotel);
        } else {
            // Handle the case where the hotel with the given ID doesn't exist
            return null;
        }
    }
    
    private HotelDTO convertToHotelDTO(HotelDTO hotelRequest) {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setName(hotelRequest.getName());
        hotelDTO.setAddress(hotelRequest.getAddress());
        hotelDTO.setCity(hotelRequest.getCity());
        hotelDTO.setState(hotelRequest.getState());
        hotelDTO.setCountry(hotelRequest.getCountry());
        hotelDTO.setDescription(hotelRequest.getDescription());
        hotelDTO.setPhoneNumber(hotelRequest.getPhoneNumber());
        hotelDTO.setEmail(hotelRequest.getEmail());
        hotelDTO.setWebsite(hotelRequest.getWebsite());
        hotelDTO.setAmenities(hotelRequest.getAmenities());
        // You might need to set other fields as well
        return hotelDTO;
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
