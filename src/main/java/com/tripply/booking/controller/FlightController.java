package com.tripply.booking.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.FlightRequest;
import com.tripply.booking.model.response.FlightResponse;
import com.tripply.booking.service.FlightService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/flights")
@Slf4j
public class FlightController {
	@Autowired
	private FlightService flightService;

	@Operation(summary = "Add Flight",
			description = "This API will add flight.")
	@PostMapping(value = "/add-flight", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseModel<FlightResponse> addFlight(@RequestBody FlightRequest flightRequest) {
		log.info("Endpoint: /add flight start: {}", flightRequest);
		ResponseModel<FlightResponse> response = flightService.addflight(flightRequest);
		log.info("Endpoint: /add flight end : {}", flightRequest);
		return response;
	}

}
