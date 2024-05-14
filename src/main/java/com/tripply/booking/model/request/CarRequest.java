package com.tripply.booking.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRequest {

    @NotBlank(message = "Registration number must not be null")
    @Size(min = 5, max = 15, message = "Registration number must be between 5 and 15 characters long.")
    @JsonProperty("registrationNo")
    private String registrationNo;
    private String model;
    private String manufactureYear;
    private String rentalCompany;
    private String location;
    private int rate;
    private boolean availability;
}
