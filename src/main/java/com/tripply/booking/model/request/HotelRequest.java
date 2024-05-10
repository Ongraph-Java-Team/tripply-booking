package com.tripply.booking.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {

    @JsonProperty("name")
    private String name;
    @JsonProperty("registrationNumber")
    private String registrationNumber;
    @JsonProperty("address")
    private String address;
    @JsonProperty("city")
    private String city;
    @JsonProperty("stateId")
    private String stateId;
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
