package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.RoomRequest;
import com.tripply.booking.model.response.RoomBulkJobResponse;
import com.tripply.booking.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/{hotelId}/rooms")
@RestController
public class RoomController {
	@Autowired
	private RoomService roomService;

	@PostMapping("/bulk-upload")
	public ResponseEntity<ResponseModel<RoomBulkJobResponse>> rangeBulkUploadRooms(@PathVariable UUID hotelId,
																				   @RequestBody RoomRequest roomRequest) {
		log.info("Starting bulk upload for hotelId: {}", hotelId);
		ResponseModel<RoomBulkJobResponse> responses = roomService.rangeBulkUploadRooms(hotelId, roomRequest);
		log.info("Bulk upload completed for hotelId: {}", hotelId);
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/list/jobs")
	public ResponseEntity<ResponseModel<Page<RoomBulkJobResponse>>> listAllRoomBulkJobs(@RequestParam(defaultValue = "createdAt") String sortBy,
																						@RequestParam(defaultValue = "desc") String sortOrder,
																						@RequestParam(defaultValue = "0") int page,
																						@RequestParam(defaultValue = "10") int size,
																						@PathVariable UUID hotelId) {
		log.info("RoomController: endpoint: /list/jobs, method: listAllRoomBulkJobs with hotelId: {} started", hotelId);
		ResponseModel<Page<RoomBulkJobResponse>> responses = roomService.listAllRoomBulkJobs(page, size, sortBy, sortOrder, hotelId);
		log.info("RoomController: endpoint: /list/jobs, method: listAllRoomBulkJobs with hotelId: {} ended", hotelId);
		return ResponseEntity.ok(responses);
	}

}