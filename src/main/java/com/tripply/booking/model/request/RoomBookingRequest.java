package com.tripply.booking.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingRequest {
    @NotBlank(message = "CheckInTime can't be blank or null")
    private String checkInTime;
    @NotBlank(message = "CheckOutTime can't be blank or null")
    private String checkOutTime;
    @NotBlank(message = "Category can't be blank or null")
    private String category;
    @NotBlank(message = "Type can't be blank or null")
    private String type;
    @NotNull(message = "RoomCount can't be blank or null")
    private int roomCount;
    @NotNull(message = "RoomCount can't be blank or null")
    private Double totalCharge;
}