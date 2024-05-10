package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CarRequest;
import com.tripply.booking.model.response.CarDetailsResponse;
import com.tripply.booking.model.response.CarResponse;
import com.tripply.booking.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping("/cars")
    public ResponseModel<CarResponse> saveCarForRent(@RequestBody CarRequest carRequest){
        log.info("Endpoint: /cars triggered with new Car: {}", carRequest.getModel());
        ResponseModel<CarResponse> carResponse = carService.saveCarForRent(carRequest);
        log.info("Endpoint: /cars triggered with new Car: {}", carRequest.getModel());
        return carResponse;
    }

    @GetMapping("/cars")
    public CarDetailsResponse getAllCars(){
        log.info("Endpoint: /get cars detail triggered: {}");
        CarDetailsResponse response = carService.getAllCars();
        log.info("Endpoint: /get cars detail triggered: {}");
        return response;
    }
}
