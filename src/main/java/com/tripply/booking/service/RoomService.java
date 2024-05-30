package com.tripply.booking.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.response.RoomUploadResponse;

public interface RoomService {

	ResponseModel<List<RoomUploadResponse>> uploadRooms(UUID hotelId, MultipartFile file) throws Exception;

}
