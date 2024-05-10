package com.tripply.booking.serviceimpl;

import com.tripply.booking.exception.DataNotFoundException;
import com.tripply.booking.model.CarDetails;
import com.tripply.booking.model.response.CustomCarDetailsResponse;
import com.tripply.booking.repository.CarRepository;
import com.tripply.booking.service.CarRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class CarRentServiceImpl implements CarRentService {


    @Autowired
    CarRepository carRepository;

    public CustomCarDetailsResponse getAllCars(int pageNo, int pageSize, String sortBy) {
        CustomCarDetailsResponse customCarDetailsResponse = new CustomCarDetailsResponse();
        try {
            Pageable pageable =  PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            List<CarDetails> details = carRepository.findAll();
            if (details.isEmpty()){
                throw new DataNotFoundException("Cars are not available");
            }
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        return customCarDetailsResponse;
    }
}
