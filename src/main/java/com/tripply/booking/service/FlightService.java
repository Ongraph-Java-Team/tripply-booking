package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.FlightRequest;
import com.tripply.booking.model.response.FlightResponse;

public interface FlightService {

	ResponseModel<FlightResponse> addflight(FlightRequest flightRequest);

}
