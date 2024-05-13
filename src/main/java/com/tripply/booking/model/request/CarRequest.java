package com.tripply.booking.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.Location;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    @NotNull(message = "Registration number must not be null")
    @Size(max = 12, message = "Registration number must be at most 12 characters")
    private String registrationNo;
    private String model;
    private String manufactureYear;
    private String rentalCompany;
    private String location;
    private int rate;
    private boolean availability;
}
