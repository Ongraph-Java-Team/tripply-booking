package com.tripply.booking.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDetailsRequest {

    @JsonProperty("userId")
    private UUID userId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    
}
