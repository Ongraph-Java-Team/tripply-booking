package com.tripply.booking.model;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel<T> {
	
	private HttpStatus status;
    private String message;
    private T data;
    
}
