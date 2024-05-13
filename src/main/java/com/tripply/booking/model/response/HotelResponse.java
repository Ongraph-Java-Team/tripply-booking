package com.tripply.booking.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tripply.booking.model.request.AmentiesRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelResponse {

    private UUID id;
    private String name;
    private String registrationNumber;
    private String adminName;
    private String adminEmail;
    private String phoneNumber;
    private String countryCode;
    private String address;
    private String city;
    private String stateId;
    private String countryId;
    private String description;
    private String website;
    private List<AmentiesRequest> amenities;

}
