package com.tripply.booking.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {

    @NotBlank(message = "hotel name can't be empty")
    @JsonProperty("name")
    private String name;
    @NotBlank(message = "Registration number can't be empty")
    @JsonProperty("registrationNumber")
    private String registrationNumber;
    @NotBlank(message = "address can't be empty")
    @JsonProperty("address")
    private String address;
    @NotBlank(message = "city can't be empty")
    @JsonProperty("city")
    private String city;
    @NotBlank(message = "stateId can't be empty")
    @JsonProperty("stateId")
    private String stateId;
    @NotBlank(message = "countryId can't be empty")
    @JsonProperty("countryId")
    private String countryId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("website")
    private String website;
    @JsonProperty("amenities")
    private List<AmentiesRequest> amenities;
    @JsonProperty("managerDetails")
    private ManagerDetailsRequest managerDetails;
    
}
