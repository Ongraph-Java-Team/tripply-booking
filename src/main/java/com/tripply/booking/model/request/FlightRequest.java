package com.tripply.booking.model.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequest {

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