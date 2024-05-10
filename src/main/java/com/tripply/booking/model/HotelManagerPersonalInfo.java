package com.tripply.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelManagerPersonalInfo {

    private String countryCode;
    private String phoneNumber;
    private String email;
    private String city;
    private String stateId;
    private String address;
    private String countryId;
    private String pinCode;

}
