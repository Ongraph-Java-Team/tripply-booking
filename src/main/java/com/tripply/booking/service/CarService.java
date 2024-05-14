package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CarRequest;
import com.tripply.booking.model.request.UpdateCarRequest;
import com.tripply.booking.model.response.CarResponse;

import java.util.List;

public interface CarService {

    ResponseModel<CarResponse> saveCarForRent(CarRequest carRequest);
    ResponseModel<List<CarResponse>> getAllCars();
    ResponseModel<CarResponse> updateCarDetails(Long carId, UpdateCarRequest updateCarRequest);
    ResponseModel<String> removeCar(Long carId);
}
