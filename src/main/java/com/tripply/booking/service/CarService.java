package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CarRequest;
import com.tripply.booking.model.response.CarDetailsResponse;
import com.tripply.booking.model.response.CarResponse;
import org.springframework.http.ResponseEntity;

public interface CarService {

    ResponseModel<CarResponse> saveCarForRent(CarRequest carRequest);

    CarDetailsResponse getAllCars();

    ResponseModel<CarResponse> updateCarDetails(Long carId, CarRequest carRequest);

    ResponseModel<String> removeCar(Long carId);
}
