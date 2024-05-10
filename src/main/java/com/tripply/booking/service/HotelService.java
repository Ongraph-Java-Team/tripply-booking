package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.response.InviteResponse;

public interface HotelService {

    ResponseModel<InviteResponse> createHotel(HotelRequest hotelRequest);

}
