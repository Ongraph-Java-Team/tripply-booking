package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.FlightRequest;
import com.tripply.booking.model.response.FlightResponse;

import java.util.List;

public interface FlightService {

	ResponseModel<FlightResponse> addflight(FlightRequest flightRequest);
	ResponseModel<List<FlightResponse>> getAllflights();
	ResponseModel<FlightResponse> updateFlightById(Long flightId, FlightRequest flightRequest);
	ResponseModel<FlightResponse> deleteFlightById(Long flightId);

}
