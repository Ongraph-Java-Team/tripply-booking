package com.tripply.booking.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingConstant {

    public static final String BEARER = "Bearer ";
    public static final String SEND_HOTEL_INVITE_URL = "/notification/send-hotel-invite";
    public static final String DUMMY_TOKEN = "dummy-token";

}
