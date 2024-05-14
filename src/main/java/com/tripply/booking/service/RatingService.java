package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.RatingRequest;
import com.tripply.booking.model.response.RatingResponse;

public interface RatingService {

    ResponseModel<RatingResponse> addRating(RatingRequest ratingRequest);

}
