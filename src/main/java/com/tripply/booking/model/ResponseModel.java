package com.tripply.booking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel<T> {

    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
    private List<ErrorDetails> errors;

    public ResponseModel(T data, String message, HttpStatus status) {
        this.data = data;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }
}
