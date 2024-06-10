package com.tripply.booking.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailsRequest {

    private String roomRange;
    private Integer floor;
    private String howToReach;
    private String category;
    private String type;
    private Double price;
    private String description;
    private List<Long> amenities;
    private List<AmentiesRequest> extraAmenities;

}
