package com.tripply.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingResponse {

    private Integer roomNumber;

    private Integer floor;

    private String roomCategory;

    private String type;

    private String howToReach;

    private Double roomPrice;

    private Double totalCharge;

    private int totalRoomBooked;
}
