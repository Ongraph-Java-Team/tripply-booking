package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.dto.CarDetails;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CarRequest;
import com.tripply.booking.model.response.CarDetailsResponse;
import com.tripply.booking.model.response.CarResponse;
import com.tripply.booking.repository.CarRepository;
import com.tripply.booking.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepository carRepository;

    public ResponseModel<CarResponse> saveCarForRent(CarRequest carRequest) {
        ResponseModel<CarResponse> response = new ResponseModel<>();
        Optional<CarDetails> carDetails1 = carRepository.getCarDetailsByRegistrationNo(carRequest.getRegistrationNo());
        CarResponse carResponse = new CarResponse();
        if (carDetails1.isPresent()) {
            response.setStatus(HttpStatus.FOUND);
            response.setMessage("Car is already added with same Register number");
            response.setData(carResponse);
            return response;
        }
        CarDetails carDetails = new CarDetails();
        carDetails.setRegistrationNo(carRequest.getRegistrationNo());
        carDetails.setModel(carRequest.getModel());
        carDetails.setYear(carRequest.getManufactureYear());
        carDetails.setAvailability(carRequest.isAvailability());
        carDetails.setRate(carRequest.getRate());
        carDetails.setRentalCompany(carRequest.getRentalCompany());
        carDetails.setLocation(carRequest.getLocation());
        carRepository.save(carDetails);
        response.setMessage("Car added successfully");
        response.setStatus(HttpStatus.CREATED);
        return response;
    }

    public CarDetailsResponse getAllCars() {
        CarDetailsResponse reponse = new CarDetailsResponse();
        try {
            List<CarDetails> carDetails = carRepository.findAll();
            if (carDetails.isEmpty()){
                throw new DataNotFoundException("Cars are not available");
            }
            reponse.setCarDetails(carDetails);
            return reponse;
        } catch (DataNotFoundException e) {
            throw new DataNotFoundException();
        }
    }
}
