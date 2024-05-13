package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.response.HotelResponse;
import com.tripply.booking.model.response.InviteResponse;

import java.util.UUID;

public interface HotelService {

    ResponseModel<InviteResponse> createHotel(HotelRequest hotelRequest);
    ResponseModel<HotelResponse> getHotelById(UUID hotelId);

}
