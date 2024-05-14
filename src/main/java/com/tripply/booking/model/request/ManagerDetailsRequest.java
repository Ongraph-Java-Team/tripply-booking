package com.tripply.booking.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "manager email can't be empty")
    @JsonProperty("email")
    private String email;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @NotBlank(message = "countryCode can't be empty")
    @JsonProperty("countryCode")
    private String countryCode;
    @NotBlank(message = "firstName can't be empty")
    @JsonProperty("firstName")
    private String firstName;
    @NotBlank(message = "lastName can't be empty")
    @JsonProperty("lastName")
    private String lastName;
    
}
