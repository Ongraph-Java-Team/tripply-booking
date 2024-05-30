package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.AmentiesRequest;
import com.tripply.booking.model.response.AmenityResponse;
import com.tripply.booking.model.response.InviteResponse;

public interface AmenityService {
    ResponseModel<AmenityResponse> addAmenity(AmentiesRequest amentiesRequest);

    ResponseModel<AmenityResponse> getAmenity(Long id);
}
