package com.tripply.booking.controller;

import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CarRequest;
import com.tripply.booking.model.response.CarDetailsResponse;
import com.tripply.booking.model.response.CarResponse;
import com.tripply.booking.service.CarService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping(value = "/cars", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<CarResponse>> addCar(@Valid @RequestBody CarRequest carRequest){
            log.info("Endpoint: /cars triggered to add new Car: {}", carRequest.getModel());
//        if(carRequest.getRegistrationNo() == null || carRequest.getRegistrationNo().isEmpty()){
//            throw new DataNotFoundException("Registration number must not be null");
//        }
        ResponseModel<CarResponse> carResponse  = carService.saveCarForRent(carRequest);
        log.info("Endpoint: /cars triggered with new Car: {}", carRequest.getModel());
        return ResponseEntity.ok(carResponse);
    }

    @GetMapping("/cars")
    public CarDetailsResponse getAllCars(){
        log.info("Endpoint: /get cars detail triggered: {}");
        CarDetailsResponse response = carService.getAllCars();
        log.info("Endpoint: /get cars detail triggered: {}");
        return response;
    }

    @PutMapping("/cars/{carId}")
    public ResponseEntity<ResponseModel<CarResponse>> updateCarDetails(
            @PathVariable Long carId,
            @Valid @RequestBody CarRequest carRequest) {
        log.info("Endpoint: /cars/{} triggered with updated details: {}", carId, carRequest);
        ResponseModel<CarResponse> response = carService.updateCarDetails(carId, carRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cars/{carId}")
    public ResponseEntity<ResponseModel<String>> deleteCar(@PathVariable Long carId) {
        log.info("Endpoint: /cars/{} triggered to delete car details: {}", carId);
        ResponseModel<String> response = carService.removeCar(carId);
        return ResponseEntity.ok(response);
    }
}
