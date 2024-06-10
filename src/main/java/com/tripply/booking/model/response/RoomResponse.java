package com.tripply.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponse {

    private Long id;
    private Integer roomNumber;
    private Integer floor;
    private String category;
    private String type;
    private String howToReach;
    private Double price;
    private String description;
    private LocalDateTime createdAt;

}
