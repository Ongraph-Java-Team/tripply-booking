package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.entity.Amenity;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.AmentiesRequest;
import com.tripply.booking.model.response.AmenityResponse;
import com.tripply.booking.repository.AmenityRepository;
import com.tripply.booking.service.AmenityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AmenityServiceImpl implements AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    @Override
    public ResponseModel<AmenityResponse> addAmenity(AmentiesRequest amentiesRequest) {
        log.info("Start AmenityServiceImpl addAmenity(): {}", amentiesRequest.getAmenityName());
        boolean exists = amenityRepository.existsByAmenityName(amentiesRequest.getAmenityName());
        if (exists) {
            throw new BadRequestException("Amenity with this name already exists.");
        }
        Amenity amenity = new Amenity();
        amenity.setAmenityName(amentiesRequest.getAmenityName());
        amenity.setDescription(amentiesRequest.getDescription());
        amenity.setIconUrl(null);

        Amenity savedAmenity = amenityRepository.save(amenity);

        AmenityResponse amenityResponse = new AmenityResponse(
                savedAmenity.getId(),
                savedAmenity.getAmenityName(),
                savedAmenity.getDescription(),
                savedAmenity.getIconUrl()
        );
        ResponseModel<AmenityResponse> response = new ResponseModel<>();
        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Amenity created successfully");
        response.setData(amenityResponse);
        log.info("End AmenityServiceImpl addAmenity(): {}", amentiesRequest.getAmenityName());
        return response;
    }

    @Override
    public ResponseModel<AmenityResponse> getAmenity(Long id) {
        Optional<Amenity> optionalAmenity = amenityRepository.findById(id);
        if (optionalAmenity.isPresent()) {
            // Convert the Amenity entity to an AmenityResponse
            Amenity amenity = optionalAmenity.get();
            AmenityResponse amenityResponse = new AmenityResponse(
                    amenity.getId(),
                    amenity.getAmenityName(),
                    amenity.getDescription(),
                    amenity.getIconUrl()
            );
            ResponseModel<AmenityResponse> response = new ResponseModel<>();
            response.setStatus(HttpStatus.OK);
            response.setMessage("Fetch Amenity Successfully");
            response.setData(amenityResponse);
            return response;
        } else {
            throw new DataNotFoundException("Amenity not found");
        }
    }
}
