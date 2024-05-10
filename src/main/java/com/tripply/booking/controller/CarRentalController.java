package com.tripply.booking.controller;

import com.tripply.booking.model.response.CustomCarDetailsResponse;
import com.tripply.booking.service.CarRentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CarRentalController {

    @Autowired
    CarRentService carRentService;

    @GetMapping("/cars")
    public CustomCarDetailsResponse getAllCars(@RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
                                               @RequestParam(name = "sortBy", required = false, defaultValue = "createdOn") String sortBy){
        log.info("Endpoint: /get cars detail triggered: {}");
        CustomCarDetailsResponse customCarDetailsResponse = carRentService.getAllCars(pageNo, pageSize, sortBy);
        log.info("Endpoint: /get cars detail triggered: {}");
        return customCarDetailsResponse;
    }
}
