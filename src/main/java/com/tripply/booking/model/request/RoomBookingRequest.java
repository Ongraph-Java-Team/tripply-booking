package com.tripply.booking.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingRequest {

    @NotNull
    private String userId;
    @NotNull
    private String checkInTime;
    @NotNull
    private String checkOutTime;
    @NotNull
    private String category;
    @NotNull
    private String type;
    @NotNull
    private int roomCount;
    @NotNull
    private Double totalCharge;
}
