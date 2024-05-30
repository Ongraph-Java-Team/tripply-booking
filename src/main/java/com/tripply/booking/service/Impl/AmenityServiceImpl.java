package com.tripply.booking.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.entity.Amenity;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.AmentiesRequest;
import com.tripply.booking.model.response.AmenityResponse;
import com.tripply.booking.repository.AmenityRepository;
import com.tripply.booking.service.AmenityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AmenityServiceImpl implements AmenityService {

	@Autowired
	private AmenityRepository amenityRepository;

	@Override
	public ResponseModel<AmenityResponse> addAmenity(AmentiesRequest amentiesRequest) {
		log.info("Start AmenityServiceImpl addAmenity(): {}", amentiesRequest.getAmenityName());
		if (amenityRepository.existsByAmenityName(amentiesRequest.getAmenityName())) {
			throw new BadRequestException("Amenity with this name already exists.");
		}
		Amenity amenity = new Amenity();
		amenity.setAmenityName(amentiesRequest.getAmenityName());
		amenity.setDescription(amentiesRequest.getDescription());
		amenity.setIconUrl(null);

		Amenity savedAmenity = amenityRepository.save(amenity);

		AmenityResponse amenityResponse = new AmenityResponse(savedAmenity.getId(), savedAmenity.getAmenityName(),
				savedAmenity.getDescription(), savedAmenity.getIconUrl());
		ResponseModel<AmenityResponse> response = new ResponseModel<>();
		response.setStatus(HttpStatus.CREATED);
		response.setMessage("Amenity created successfully");
		response.setData(amenityResponse);
		log.info("End AmenityServiceImpl addAmenity(): {}", amentiesRequest.getAmenityName());
		return response;
	}

	@Override
	public ResponseModel<AmenityResponse> getAmenity(Long id) {
		log.info("Start AmenityServiceImpl getAmenity(): {}", id);
		try {
			Amenity amenity = amenityRepository.findById(id)
					.orElseThrow(() -> new DataNotFoundException("Amenity not found"));

			AmenityResponse amenityResponse = new AmenityResponse(amenity.getId(), amenity.getAmenityName(),
					amenity.getDescription(), amenity.getIconUrl());

			ResponseModel<AmenityResponse> response = new ResponseModel<>();
			response.setStatus(HttpStatus.OK);
			response.setMessage("Fetch Amenity Successfully");
			response.setData(amenityResponse);
			log.info("End AmenityServiceImpl getAmenity(): {}", id);
			return response;
		} catch (DataNotFoundException e) {
			log.error("Amenity not found for id: {}", id);
			ResponseModel<AmenityResponse> errorResponse = new ResponseModel<>();
			errorResponse.setStatus(HttpStatus.NOT_FOUND);
			errorResponse.setMessage("Amenity not found");
			return errorResponse;
		}
	}

	@Override
	public ResponseModel<List<AmenityResponse>> getAllAmenities() {
		ResponseModel<List<AmenityResponse>> response = new ResponseModel<>();
		List<Amenity> amenities = amenityRepository.findAll();
		if (amenities.isEmpty()) {
			throw new DataNotFoundException("Amenity not found");
		}
		List<AmenityResponse> amenityResponses = amenities.stream()
				.map(this::mapToAmenityResponse)
				.collect(Collectors.toList());
		response.setStatus(HttpStatus.OK);
		response.setMessage("Amenities retrieved successfully.");
		response.setData(amenityResponses);
		return response;
	}

	private AmenityResponse mapToAmenityResponse(Amenity amenity) {
		AmenityResponse amenityResponse = new AmenityResponse();
		amenityResponse.setId(amenity.getId());
		amenityResponse.setAmenityName(amenity.getAmenityName());
		amenityResponse.setDescription(amenity.getDescription());
		amenityResponse.setIconURL(amenity.getIconUrl());
		return amenityResponse;
	}
}
