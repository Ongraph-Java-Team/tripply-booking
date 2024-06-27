package com.tripply.booking.service;

import com.tripply.booking.entity.Room;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.RoomBookingRequest;
import com.tripply.booking.model.request.RoomRequest;
import com.tripply.booking.model.response.RoomAvailableResponse;
import com.tripply.booking.model.response.RoomBookingResponse;
import com.tripply.booking.model.response.RoomBulkJobResponse;
import com.tripply.booking.model.response.RoomResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface RoomService {

    ResponseModel<RoomBulkJobResponse> rangeBulkUploadRooms(UUID hotelId, RoomRequest roomRequest);
    ResponseModel<Page<RoomBulkJobResponse>> listAllRoomBulkJobs(int page, int size, String sortBy, String sortOrder, UUID hotelId);
    ResponseModel<Page<RoomResponse>> listAllRooms(int page, int size, String sortBy, String sortOrder, UUID hotelId);
    ResponseModel<Room> getRoomDetailsById(Long id);
    ResponseModel<RoomBookingResponse> bookRoom(UUID hotelId, RoomBookingRequest request);
    ResponseModel<RoomAvailableResponse> checkRoomAvailability(UUID hotelId, String checkInTime, String checkOutTime, String category, String roomType, int roomCount);
}
