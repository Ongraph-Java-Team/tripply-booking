package com.tripply.booking.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.response.RoomUploadResponse;
import com.tripply.booking.service.RoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/hotels/{hotelId}/rooms")
@RestController
public class RoomController {
	@Autowired
	private RoomService roomService;

	@PostMapping("/bulk-upload")
	public ResponseEntity<ResponseModel<List<RoomUploadResponse>>> bulkUploadRooms(@PathVariable UUID hotelId,
			@RequestParam("file") MultipartFile file) throws Exception {
		log.info("Starting bulk upload for hotelId: {}", hotelId);

		ResponseModel<List<RoomUploadResponse>> responses = roomService.uploadRooms(hotelId, file);
		log.info("Bulk upload completed for hotelId: {}", hotelId);

		return ResponseEntity.ok(responses);
	}
}