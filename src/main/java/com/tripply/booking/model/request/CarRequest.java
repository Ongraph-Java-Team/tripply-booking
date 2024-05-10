package com.tripply.booking.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {
    private String registrationNo;
    private String model;
    private String manufactureYear;
    private String rentalCompany;
    private String location;
    private int rate;
    private boolean availability;
}
