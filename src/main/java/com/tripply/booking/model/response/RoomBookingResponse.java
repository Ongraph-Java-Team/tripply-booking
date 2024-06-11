package com.tripply.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingResponse {

    private Long bookingId;

    private UUID userId;

    private String userName;

    private UUID hotelId;

    private String hotelName;

    private List<Integer> roomNumbers;

    private String roomCategory;

    private String roomType;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private int totalRoomBooked;

    private double totalCharge;

    private Object invoiceDetails;
}
