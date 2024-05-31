package com.tripply.booking.service;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.RoomRequest;
import com.tripply.booking.model.response.RoomBulkJobResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface RoomService {

    ResponseModel<RoomBulkJobResponse> rangeBulkUploadRooms(UUID hotelId, RoomRequest roomRequest);
    ResponseModel<Page<RoomBulkJobResponse>> listAllRoomBulkJobs(int page, int size, String sortBy, String sortOrder, UUID hotelId);

}
