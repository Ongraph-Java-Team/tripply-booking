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
    @NotNull(message = "It can't be null")
    @NotBlank(message = "It can't be blank")
    private String checkInTime;
    @NotNull(message = "It can't be null")
    @NotBlank(message = "It can't be blank")
    private String checkOutTime;
    @NotNull(message = "It can't be null")
    @NotBlank(message = "It can't be blank")
    private String category;
    @NotNull(message = "It can't be null")
    @NotBlank(message = "It can't be blank")
    private String type;
    @NotNull(message = "It can't be null")
    private int roomCount;
    @NotNull(message = "It can't be null")
    private Double totalCharge;
}