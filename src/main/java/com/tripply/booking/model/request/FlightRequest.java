package com.tripply.booking.model.request;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequest {

	@NotNull(message = "Flight number must not be null")
	@Size(min = 5, max = 15, message = "Flight number must be between 5 and 15 characters long.")
	@Column(unique = true)
	private String flightNo;
	private String airline;
	private String origin;
	private String destination;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private int seatsAvailable;
}