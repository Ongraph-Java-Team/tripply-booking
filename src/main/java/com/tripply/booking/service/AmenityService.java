package com.tripply.booking.service;

import java.util.List;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.AmentiesRequest;
import com.tripply.booking.model.response.AmenityResponse;

public interface AmenityService {
    ResponseModel<AmenityResponse> addAmenity(AmentiesRequest amentiesRequest);

    ResponseModel<AmenityResponse> getAmenity(Long id);

	ResponseModel<List<AmenityResponse>> getAllAmenities();
}
