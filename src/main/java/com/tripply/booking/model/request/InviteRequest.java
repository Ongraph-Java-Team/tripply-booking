package com.tripply.booking.model.request;

import com.tripply.booking.constants.enums.InvitationCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InviteRequest {

    private String sentToEmail;
    private InvitationCategory category;
    private String sendToName;
    private HotelRequest hotelRequestBean;

}
