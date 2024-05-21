package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CarRequest;
import com.tripply.booking.model.request.UpdateCarRequest;
import com.tripply.booking.model.response.CarResponse;
import com.tripply.booking.service.CarService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class CarController {

    @Autowired
    CarService carService;

    @PostMapping(value = "/cars", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseModel<CarResponse>> addCar(@Valid @RequestBody CarRequest carRequest) {
        log.info("Start Endpoint: /cars triggered to add new Car: {}", carRequest.getModel());
        ResponseModel<CarResponse> carResponse = carService.saveCarForRent(carRequest);
        log.info("End Endpoint: /cars triggered to add new Car: {}", carRequest.getModel());
        return ResponseEntity.ok(carResponse);
    }

    @GetMapping("/cars")
    public ResponseEntity<ResponseModel<List<CarResponse>>> getAllCars() {
        log.info("Start Endpoint: /get triggered to get All Cars detail");
        ResponseModel<List<CarResponse>> response = carService.getAllCars();
        log.info("End Endpoint: /get triggered to get All Cars detail");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/cars/{carId}")
    public ResponseEntity<ResponseModel<CarResponse>> updateCarDetails(
            @PathVariable Long carId, @RequestBody UpdateCarRequest updateCarRequest) {
        log.info("Start Endpoint: /cars/{} triggered with updated details: {}", carId, updateCarRequest);
        ResponseModel<CarResponse> response = carService.updateCarDetails(carId, updateCarRequest);
        log.info("End Endpoint: /cars/{} triggered with updated details: {}", carId, updateCarRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cars/{carId}")
    public ResponseEntity<ResponseModel<String>> deleteCar(@PathVariable Long carId) {
        log.info("Start Endpoint: /cars/{} triggered to delete car details", carId);
        ResponseModel<String> response = carService.removeCar(carId);
        log.info("End Endpoint: /cars/{} triggered to delete car details", carId);
        return ResponseEntity.ok(response);
    }
}
