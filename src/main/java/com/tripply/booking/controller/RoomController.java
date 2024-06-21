package com.tripply.booking.controller;

import com.tripply.booking.entity.Room;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.RoomBookingRequest;
import com.tripply.booking.model.request.RoomRequest;
import com.tripply.booking.model.response.RoomAvailableResponse;
import com.tripply.booking.model.response.RoomBookingResponse;
import com.tripply.booking.model.response.RoomBulkJobResponse;
import com.tripply.booking.model.response.RoomResponse;
import com.tripply.booking.service.RoomService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequestMapping("/rooms")
@RestController
public class RoomController {
	@Autowired
	private RoomService roomService;

	@PostMapping("/bulk-upload/{hotelId}")
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
																						@RequestParam(required = false) UUID hotelId) {
		log.info("RoomController: endpoint: /list/jobs, method: listAllRoomBulkJobs with hotelId: {} started", hotelId);
		ResponseModel<Page<RoomBulkJobResponse>> responses = roomService.listAllRoomBulkJobs(page, size, sortBy, sortOrder, hotelId);
		log.info("RoomController: endpoint: /list/jobs, method: listAllRoomBulkJobs with hotelId: {} ended", hotelId);
		return ResponseEntity.ok(responses);
	}

	@GetMapping
	public ResponseEntity<ResponseModel<Page<RoomResponse>>> listAllRooms(@RequestParam(defaultValue = "createdAt") String sortBy,
																		  @RequestParam(defaultValue = "desc") String sortOrder,
																		  @RequestParam(defaultValue = "0") int page,
																		  @RequestParam(defaultValue = "10") int size,
																		  @RequestParam(required = false) UUID hotelId) {
		log.info("RoomController: method: listAllRooms with hotelId: {} started", hotelId);
		ResponseModel<Page<RoomResponse>> responses = roomService.listAllRooms(page, size, sortBy, sortOrder, hotelId);
		log.info("RoomController: method: listAllRooms with hotelId: {} ended", hotelId);
		return ResponseEntity.ok(responses);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseModel<Room>> getRoomDetailsById(@PathVariable Long id) {
		log.info("RoomController: endpoint: /{id}, method: getRoomDetailsById with id: {} started", id);
		ResponseModel<Room> responses = roomService.getRoomDetailsById(id);
		log.info("RoomController: endpoint: /{id}, method: getRoomDetailsById with id: {} ended", id);
		return ResponseEntity.ok(responses);
	}

	@PostMapping("{hotelId}/book")
	public ResponseEntity<ResponseModel<RoomBookingResponse>> bookRoom(@PathVariable UUID hotelId, @RequestBody @Valid RoomBookingRequest request) {
		log.info("RoomController: endpoint: /{hotelId}/book, method: bookRoom with id: {} started", hotelId);
		ResponseModel<RoomBookingResponse> responses = roomService.bookRoom(hotelId, request);
		log.info("RoomController: endpoint: /{hotelId}/book, method: bookRoom with id: {} ended", hotelId);
		return ResponseEntity.ok(responses);
	}

	@GetMapping("{hotelId}/available-rooms")
	public ResponseEntity<ResponseModel<RoomAvailableResponse>> checkRoomAvailability(@PathVariable UUID hotelId,
																					  @RequestParam String checkInTime,
																					  @RequestParam String checkOutTime,
																					  @RequestParam String category,
																					  @RequestParam String roomType,
																					  @RequestParam int roomCount) {
		log.info("RoomController: endpoint: /{hotelId}/available-rooms, method: bookRoom with id: {} started", hotelId);
		ResponseModel<RoomAvailableResponse> responses = roomService.checkRoomAvailability(hotelId, checkInTime, checkOutTime, category, roomType, roomCount);
		log.info("RoomController: endpoint: /{hotelId}/available-rooms, method: bookRoom with id: {} ended", hotelId);
		return ResponseEntity.ok(responses);
	}
}