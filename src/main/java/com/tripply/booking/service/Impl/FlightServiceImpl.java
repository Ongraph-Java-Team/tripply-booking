package com.tripply.booking.service.Impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tripply.booking.Exception.FailToSaveException;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.FlightRequest;
import com.tripply.booking.model.response.FlightResponse;
import com.tripply.booking.repository.FlightRepository;
import com.tripply.booking.service.FlightService;

@Service
public class FlightServiceImpl implements FlightService {

	@Autowired
    FlightRepository flightRepository;
	

	@Override
	public ResponseModel<FlightResponse> addflight(FlightRequest flightRequest) {
		// TODO Auto-generated method stub
		ResponseModel<FlightResponse> response = null;

		try {
			flightRequest.setCreatedAt(LocalDateTime.now());
			flightRequest.setUpdatedAt(LocalDateTime.now());
			flightRepository.save(flightRequest);
			FlightResponse flightResponse = new FlightResponse();
			response = new ResponseModel<>();
			flightResponse.setFlightId(flightRequest.getFlightId());
			flightResponse.setAirline(flightRequest.getAirline());
			flightResponse.setOrigin(flightRequest.getOrigin());
			flightResponse.setDestination(flightRequest.getDestination());
			flightResponse.setDepartureTime(flightRequest.getDepartureTime());
			flightResponse.setArrivalTime(flightRequest.getArrivalTime());
			flightResponse.setSeatsAvailable(flightRequest.getSeatsAvailable());
			flightResponse.setCreatedAt(flightRequest.getCreatedAt());
			flightResponse.setUpdatedAt(flightRequest.getUpdatedAt());
			response.setStatus(HttpStatus.CREATED);
			response.setMessage("Flight added successfully.");
			response.setData(flightResponse);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new FailToSaveException("not created flight");
		}
		return response;
	}

}
