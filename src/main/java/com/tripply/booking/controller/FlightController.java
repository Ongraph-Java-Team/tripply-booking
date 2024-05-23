package com.tripply.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.FlightRequest;
import com.tripply.booking.model.response.FlightResponse;
import com.tripply.booking.service.FlightService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/flights")
@Slf4j
public class FlightController {
	@Autowired
	private FlightService flightService;

	@PostMapping
	public ResponseEntity<ResponseModel<FlightResponse>> addFlight(@RequestBody FlightRequest flightRequest) {
		log.info("Start Endpoint: /add flight start: {}", flightRequest);
		ResponseModel<FlightResponse> response = flightService.addflight(flightRequest);
		log.info("End Endpoint: /add flight end : {}", flightRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<ResponseModel<List<FlightResponse>>> getFlight() {
		log.info("Start Endpoint: /get flight ");
		ResponseModel<List<FlightResponse>> response = flightService.getAllflights();
		log.info("End Endpoint: /get flight ");
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{flightId}")
	public ResponseEntity<ResponseModel<FlightResponse>> updateFlight(@PathVariable Long flightId, @RequestBody FlightRequest flightRequest) {
		log.info("Start Endpoint: /update flight : {}", flightRequest);
		ResponseModel<FlightResponse> response = flightService.updateFlightById(flightId,flightRequest);
		log.info("End Endpoint: /update flight : {}", flightRequest);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{flightId}")
	public ResponseEntity<ResponseModel<FlightResponse>> deleteFlight(@PathVariable Long flightId) {
		log.info("Start Endpoint: /delete flight : {}", flightId);
		ResponseModel<FlightResponse> response = flightService.deleteFlightById(flightId);
		log.info("Start Endpoint: /delete flight : {}", flightId);
		return ResponseEntity.ok(response);
	}

}