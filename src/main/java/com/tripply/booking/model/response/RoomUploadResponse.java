package com.tripply.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomUploadResponse {
	private String roomNumber;
    private String status;
    private String message;

}
