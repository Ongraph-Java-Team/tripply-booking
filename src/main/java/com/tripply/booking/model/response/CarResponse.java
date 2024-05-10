package com.tripply.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.Location;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {

    private String carId;
    private String registrationNo;
    private String model;
    private String year;
    private String rentalCompany;
    private String location;
    private int rate;
    private boolean availability;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
