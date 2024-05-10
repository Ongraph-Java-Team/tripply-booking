package com.tripply.booking.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InviteResponse {

    private String invitationId;
    private String name;
    private String sendToEmail;
    private String adminFullName;
    private String phoneNumber;

}

