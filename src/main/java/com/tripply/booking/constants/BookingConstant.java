package com.tripply.booking.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingConstant {

    public static final String BEARER = "Bearer ";
    public static final String SEND_HOTEL_INVITE_URL = "/notification/send-hotel-invite";
    public static final String CHECK_ALREADY_INVITED = "/notification/check-invitation/";
    public static final String CHECK_USER_ALREADY_EXIST = "/auth/user/";
    public static final String FILE_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String FILE_TYPE_CSV = "text/csv";

}
