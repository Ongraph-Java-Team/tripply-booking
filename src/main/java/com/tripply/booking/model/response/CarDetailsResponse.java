package com.tripply.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.Location;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDetailsResponse {

    private String carId;
    private String make;
    private String model;
    private String year;
    private String rentalCompany;
    private Location location;
    private int rate;
    private boolean availability;

}
