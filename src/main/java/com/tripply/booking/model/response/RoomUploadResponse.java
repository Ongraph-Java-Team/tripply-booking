package com.tripply.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomUploadResponse {
	private Integer roomNumber;
    private Integer status;
    private String message;

}
