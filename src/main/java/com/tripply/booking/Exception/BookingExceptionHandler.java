package com.tripply.booking.Exception;

import com.tripply.booking.constants.enums.ErrorConstant;
import com.tripply.booking.model.ErrorDetails;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@ControllerAdvice
public class BookingExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseModel<String>> handleBadRequestException(BadRequestException ex) {
        log.error("BadRequestException handled with message: ", ex);
        ResponseModel<String> errorResponse = new ResponseModel<>();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrors(List.of(ErrorDetails.builder()
                .errorCode(ErrorConstant.ER003.getErrorCode())
                .errorDesc(ex.getMessage())
                .build()));
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResponseModel<String>> handleDataNotFoundException(DataNotFoundException ex) {
        log.error("DataNotFoundException handled with message: ", ex);
        ResponseModel<String> errorResponse = new ResponseModel<>();
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrors(List.of(ErrorDetails.builder()
                .errorCode(ErrorConstant.ER001.getErrorCode())
                .errorDesc(ex.getMessage())
                .build()));
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceCommunicationException.class)
    public ResponseEntity<ResponseModel<String>> handleServiceCommunicationException(ServiceCommunicationException ex) {
        log.error("ServiceCommunicationException handled with message: ", ex);
        ResponseModel<String> errorResponse = new ResponseModel<>();
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrors(List.of(ErrorDetails.builder()
                .errorCode(ErrorConstant.ER002.getErrorCode())
                .errorDesc(ErrorConstant.ER002.getErrorDescription())
                .build()));
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel<String>> handleServiceCommunicationException(Exception ex) {
        log.error("ServiceCommunicationException handled with message: ", ex);
        ResponseModel<String> errorResponse = new ResponseModel<>();
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setErrors(List.of(ErrorDetails.builder()
                .errorCode(ErrorConstant.ER003.getErrorCode())
                .errorDesc(ErrorConstant.ER003.getErrorDescription())
                .build()));
        errorResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}