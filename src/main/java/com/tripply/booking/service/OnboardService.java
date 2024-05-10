package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.response.HotelResponse;

public interface OnboardService {

    ResponseModel<HotelResponse> onboardHotel(HotelRequest hotelRequest);

}
