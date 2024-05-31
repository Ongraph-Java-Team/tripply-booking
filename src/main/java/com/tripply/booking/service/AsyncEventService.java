package com.tripply.booking.service;

import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.RoomBulkJob;
import com.tripply.booking.model.request.RoomRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

public interface AsyncEventService {

    @Async
    @Transactional
    void saveRoomDetailsAsync(RoomBulkJob savedRoomBulkJob, Hotel hotel, RoomRequest roomRequest);

}
