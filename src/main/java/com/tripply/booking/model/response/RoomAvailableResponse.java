package com.tripply.booking.model.response;

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
public class RoomAvailableResponse {
    private UUID hotelId;
    private String hotelName;
    private String roomCategory;
    private String roomType;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private int availableRooms;
    private double actualCharge;
    private double totalCharge;
    private long nights;
    private String description;
}
