package com.tripply.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long id;
    private Integer roomNumber;
    private Integer floor;
    private String category;
    private String type;
    private Double price;
    private String description;
    private UUID hotelId;
}
