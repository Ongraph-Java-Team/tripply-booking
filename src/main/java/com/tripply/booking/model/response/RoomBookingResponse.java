package com.tripply.booking.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tripply.booking.constants.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomBookingResponse {
    private UUID bookingId;
    private UUID userId;
    private UUID hotelId;
    private String hotelName;
    private List<Integer> roomNumbers;
    private String roomCategory;
    private String roomType;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private int totalRoomBooked;
    private double totalCharge;
    private BookingStatus bookingStatus;
    private LocalDateTime bookingInitiatedTime;
    private Object invoiceDetails;
}
