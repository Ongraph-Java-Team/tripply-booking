package com.tripply.booking.model.response;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class FlightResponse {
	
	private Long flightId;
	private String airline;
	private String origin;
	private String destination;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private int seatsAvailable;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
