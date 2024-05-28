package com.tripply.booking.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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

		Flight flight = setFlightDetails(flightRequest);
		flight = flightRepository.save(flight);
		FlightResponse flightResponse = setFlightsInResponse(flight);
		ResponseModel<FlightResponse> response = new ResponseModel<>();
		response.setStatus(HttpStatus.CREATED);
		response.setMessage("Flight added successfully.");
		response.setData(flightResponse);
		return response;
	}

	@Override
	public ResponseModel<List<FlightResponse>> getAllflights() {
		List<Flight> flights = flightRepository.findAll();
		if (flights.isEmpty()) {
			throw new DataNotFoundException("Flight are not Available");
		}
		List<FlightResponse> flightList = flights.stream().map(this::setFlightsInResponse).collect(Collectors.toList());
		ResponseModel<List<FlightResponse>> response = new ResponseModel<>();
		response.setData(flightList);
		response.setStatus(HttpStatus.FOUND);
		response.setMessage("Flight retrieved successfully.");
		return response;
	}

	@Override
	public ResponseModel<FlightResponse> updateFlightById(Long flightId, FlightRequest flightRequest) {
		Optional<Flight> flightOptional = flightRepository.findById(flightId);
		if (flightOptional.isEmpty()) {
			throw new DataNotFoundException("Flight is not present with flightId : " + flightId);
		}
		Flight existingFlight = updateFlight(flightOptional, flightRequest);
		Flight flight = flightRepository.save(existingFlight);
		FlightResponse flightResponse = setFlightsInResponse(flight);
		ResponseModel<FlightResponse> response = new ResponseModel<>();
		response.setMessage("Flight updated successfully");
		response.setData(flightResponse);
		response.setStatus(HttpStatus.OK);
		return response;
	}

	@Override
	public ResponseModel<FlightResponse> deleteFlightById(Long flightId) {
		Optional<Flight> flightOptional = flightRepository.findById(flightId);
		if (flightOptional.isEmpty()) {
			throw new DataNotFoundException("Flight is not present with flightId : " + flightId);
		}
		flightRepository.deleteById(flightId);
		ResponseModel<FlightResponse> response = new ResponseModel<>();
		response.setStatus(HttpStatus.OK);
		response.setMessage("Flight deleted successfully");
		return response;
	}

	private Flight setFlightDetails(FlightRequest flightRequest) {
		Flight flight = new Flight();
		flight.setAirline(flightRequest.getAirline());
		flight.setDestination(flightRequest.getDestination());
		flight.setOrigin(flightRequest.getOrigin());
		flight.setDepartureTime(flightRequest.getDepartureTime());
		flight.setArrivalTime(flightRequest.getArrivalTime());
		flight.setSeatsAvailable(flightRequest.getSeatsAvailable());
		flight.setFlightNo(flightRequest.getFlightNo());
		return flight;
	}

	private FlightResponse setFlightsInResponse(Flight flight) {
		return FlightResponse.builder()
				.flightId(flight.getFlightId())
				.airline(flight.getAirline())
				.origin(flight.getOrigin())
				.destination(flight.getDestination())
				.seatsAvailable(flight.getSeatsAvailable())
				.departureTime(flight.getDepartureTime())
				.arrivalTime(flight.getArrivalTime())
				.createdAt(flight.getCreatedAt())
				.updatedAt(flight.getUpdatedAt())
				.flightNo(flight.getFlightNo())
				.build();
	}

	private Flight updateFlight(Optional<Flight> flightOptional, FlightRequest flightRequest) {
		Flight flight = flightOptional.get();

		if (flightRequest.getAirline() != null) {
			flight.setAirline(flightRequest.getAirline());
		}

		if (flightRequest.getDestination() != null) {
			flight.setDestination(flightRequest.getDestination());
		}

		if (flightRequest.getOrigin() != null) {
			flight.setOrigin(flightRequest.getOrigin());
		}

		if (flightRequest.getDepartureTime() != null) {
			flight.setDepartureTime(flightRequest.getDepartureTime());
		}

		if (flightRequest.getArrivalTime() != null) {
			flight.setArrivalTime(flightRequest.getArrivalTime());
		}

		if (flightRequest.getSeatsAvailable() != 0) {
			flight.setSeatsAvailable(flightRequest.getSeatsAvailable());
		}

		return flight;
	}
}