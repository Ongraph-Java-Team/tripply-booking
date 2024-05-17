package com.tripply.booking.model.request;

import lombok.Data;

@Data
public class UpdateCarRequest {
    private String model;
    private String manufactureYear;
    private String rentalCompany;
    private String location;
    private int rate;
    private boolean availability;
}
