package com.tripply.booking.components;

import com.tripply.booking.constants.BookingConstant;
import com.tripply.booking.constants.enums.BookingStatus;
import com.tripply.booking.entity.RoomBookingStage;
import com.tripply.booking.repository.RoomBookingRepository;
import com.tripply.booking.repository.RoomBookingStageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class BookingSchedulers {

    @Value("${app.booking.room-onboard-cleaner}")
    private String ROOM_ONBOARD_CLEANER;

    @Autowired
    RoomBookingRepository roomBookingRepository;

    @Autowired
    RoomBookingStageRepository roomBookingStageRepository;

    @Scheduled(fixedRateString = "${app.booking.room-onboard-cleaner}")
    public void cleanRoomBooking() {
        log.info("> SERVICE -> Clean Room Booking executing... >>>");
        List<RoomBookingStage> roomBookingStageList = roomBookingStageRepository.findAllByIsActive(true);

        for(RoomBookingStage roomBookingStage : roomBookingStageList) {
            if(LocalDateTime.now().isAfter(roomBookingStage.getCreatedOn().plusMinutes(BookingConstant.ROOM_ONBOARD_EXPIRATION))) {
                roomBookingRepository.deleteById(roomBookingStage.getBookingId());
                log.info("Room Booking entry of id :{} is deleted successfully.", roomBookingStage.getBookingId());
                roomBookingStage.setBookingStatus(BookingStatus.CANCELLED);
                roomBookingStage.setActive(false);
                roomBookingStageRepository.save(roomBookingStage);
                log.info("Room Booking status is set to CANCELLED and active status is set to FALSE of id: {}", roomBookingStage.getId());
            }
        }

        log.info("> SERVICE -> Clean Room Booking ended. >>>");
    }

}
