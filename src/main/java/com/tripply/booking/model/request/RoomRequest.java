package com.tripply.booking.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequest {

    private Long totalRooms;
    private List<RoomDetailsRequest> roomDetails;

}
