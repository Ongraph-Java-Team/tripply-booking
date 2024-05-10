package com.tripply.booking.service;

import com.tripply.booking.model.response.CustomCarDetailsResponse;

public interface CarRentService {

    CustomCarDetailsResponse getAllCars(int pageNo, int pageSize, String sortBy);
}
