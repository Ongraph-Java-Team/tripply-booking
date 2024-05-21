package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.TopRatedHotelProjection;
import com.tripply.booking.model.request.RatingRequest;
import com.tripply.booking.model.response.RatingResponse;

import java.util.List;
import java.util.UUID;

public interface RatingService {

    ResponseModel<RatingResponse> addRating(RatingRequest ratingRequest);
    ResponseModel<List<RatingResponse>> getAllRatings(UUID userId, UUID hotelId, Integer minRating, Integer maxRating, String sortBy, String sortOrder, int page, int size);
    ResponseModel<List<TopRatedHotelProjection>> getTopRatedHotels(Integer top);

}
