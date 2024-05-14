package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.entity.CarDetails;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CarRequest;
import com.tripply.booking.model.response.CarDetailsResponse;
import com.tripply.booking.model.response.CarResponse;
import com.tripply.booking.repository.CarRepository;
import com.tripply.booking.service.CarService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    CarRepository carRepository;

    public ResponseModel<CarResponse> saveCarForRent(CarRequest carRequest) {
        if (checkedIsCarAvailable(carRequest.getRegistrationNo())) {
            throw new BadRequestException("Car already Added.");
        }
        ResponseModel<CarResponse> response = new ResponseModel<>();
        CarDetails carDetails = getCarDetails(carRequest);
        carRepository.save(carDetails);
        response.setMessage("Car added successfully");
        response.setStatus(HttpStatus.OK);
        response.setData(setAddedCar(carRequest.getRegistrationNo()));
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

    public ResponseModel<CarResponse> updateCarDetails(Long carId, CarRequest carRequest) {
        Optional<CarDetails> optionalCarDetails = carRepository.findById(Math.toIntExact(carId));
        if (optionalCarDetails.isEmpty()) {
            throw new DataNotFoundException("Car not found with ID: " + carId);
        }

        CarDetails existingCar = updatedDetails(carRequest, optionalCarDetails);

        CarDetails carDetails = carRepository.save(existingCar);
        CarResponse carResponse = setCarDetails(carDetails);
        ResponseModel<CarResponse> response = new ResponseModel<>();
        response.setMessage("Car details updated successfully");
        response.setStatus(HttpStatus.OK);
        response.setData(carResponse);
        return response;
    }

    @Override
    public ResponseModel<String> removeCar(Long carId) {
        Optional<CarDetails> optionalCarDetails = carRepository.findById(Math.toIntExact(carId));
        if (optionalCarDetails.isEmpty()) {
            throw new DataNotFoundException("Car not found with ID: " + carId);
        }
        ResponseModel<String> response = new ResponseModel<>();
        carRepository.deleteById(Math.toIntExact(carId));
        response.setMessage("Car is delete from the system");
        response.setStatus(HttpStatus.OK);
        response.setData("Data removed");
        return response;
    }

    private static CarDetails updatedDetails(CarRequest carRequest, Optional<CarDetails> optionalCarDetails) {
        CarDetails existingCar = optionalCarDetails.get();

        if (carRequest.getModel() != null) {
            existingCar.setModel(carRequest.getModel());
        }
        if (carRequest.getManufactureYear() != null) {
            existingCar.setYear(carRequest.getManufactureYear());
        }
        if (carRequest.getRentalCompany() != null) {
            existingCar.setRentalCompany(carRequest.getRentalCompany());
        }
        if (carRequest.getLocation() != null) {
            existingCar.setLocation(carRequest.getLocation());
        }
        if (carRequest.getRate() != 0) { // Assuming rate cannot be null
            existingCar.setRate(carRequest.getRate());
        }
        if (carRequest.getManufactureYear() != null) { // Assuming rate cannot be null
            existingCar.setYear(carRequest.getManufactureYear());
        }
        return existingCar;
    }

    private CarResponse setCarDetails(CarDetails carDetails) {
    CarResponse carResponse = new CarResponse();
    carResponse.setCarId(carDetails.getCarId());
    carResponse.setLocation(carDetails.getLocation());
    carResponse.setYear(carDetails.getYear());
    carResponse.setRate(carDetails.getRate());
    carResponse.setUpdatedAt(carDetails.getUpdatedAt());
    carResponse.setCreatedAt(carDetails.getCreatedAt());
    carResponse.setAvailability(carDetails.isAvailability());
    carResponse.setModel(carDetails.getModel());
    carResponse.setRegistrationNo(carDetails.getRegistrationNo());
    carResponse.setRentalCompany(carDetails.getRentalCompany());
    return carResponse;
    }

    private static CarDetails getCarDetails(CarRequest carRequest) {
        CarDetails carDetails = new CarDetails();
        carDetails.setRegistrationNo(carRequest.getRegistrationNo());
        carDetails.setModel(carRequest.getModel());
        carDetails.setYear(carRequest.getManufactureYear());
        carDetails.setAvailability(carRequest.isAvailability());
        carDetails.setRate(carRequest.getRate());
        carDetails.setRentalCompany(carRequest.getRentalCompany());
        carDetails.setLocation(carRequest.getLocation());
        carDetails.setAvailability(true);
        return carDetails;
    }


    private boolean checkedIsCarAvailable(String registrationNo) {
        log.info("Begin checkedIsCarAvailable() for the registrationNo: {} ", registrationNo);
        Optional<CarDetails> carDetails = carRepository.getCarDetailsByRegistrationNo(registrationNo);
        return carDetails.isPresent();
    }

    private CarResponse setAddedCar(String registrationNo){
        Optional<CarDetails> carDetailsOptional = carRepository.getCarDetailsByRegistrationNo(registrationNo);
        CarResponse carResponse = new CarResponse();
        CarDetails carDetails = carDetailsOptional.get();
        carResponse.setCarId(carDetails.getCarId());
        carResponse.setRegistrationNo(carDetails.getRegistrationNo());
        carResponse.setModel(carDetails.getModel());
        carResponse.setYear(carDetails.getYear());
        carResponse.setRentalCompany(carDetails.getRentalCompany());
        carResponse.setLocation(carDetails.getLocation());
        carResponse.setRate(carDetails.getRate());
        carResponse.setAvailability(carDetails.isAvailability());
        carResponse.setCreatedAt(carDetails.getCreatedAt());
        carResponse.setUpdatedAt(carDetails.getUpdatedAt());
        return carResponse;
    }
}
