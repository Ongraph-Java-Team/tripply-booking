package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.constants.enums.JobStatus;
import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.RoomBulkJob;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.RoomRequest;
import com.tripply.booking.model.response.RoomBulkJobResponse;
import com.tripply.booking.repository.AmenityRepository;
import com.tripply.booking.repository.HotelRepository;
import com.tripply.booking.repository.RoomBulkJobRepository;
import com.tripply.booking.repository.RoomRepository;
import com.tripply.booking.service.AsyncEventService;
import com.tripply.booking.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final HotelRepository hotelRepository;
    private final AsyncEventService asyncEventService;
    private final RoomBulkJobRepository roomBulkJobRepository;
    private final AmenityRepository amenityRepository;
    private final RoomRepository roomRepository;

    public RoomServiceImpl(HotelRepository hotelRepository, AsyncEventService asyncEventService, RoomBulkJobRepository roomBulkJobRepository, AmenityRepository amenityRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.asyncEventService = asyncEventService;
        this.roomBulkJobRepository = roomBulkJobRepository;
        this.amenityRepository = amenityRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public ResponseModel<RoomBulkJobResponse> rangeBulkUploadRooms(UUID hotelId, RoomRequest roomRequest) {
        log.info("RoomService: method -> rangeBulkUploadRooms() with hotelId: {} and totalRoom: {} started", hotelId, roomRequest.getTotalRooms());
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new DataNotFoundException("Specified hotel details not found.")
        );
        RoomBulkJob roomBulkJob = new RoomBulkJob();
        roomBulkJob.setCreatedAt(LocalDateTime.now());
        roomBulkJob.setRoomRequest(roomRequest);
        roomBulkJob.setStatus(JobStatus.PENDING);
        roomBulkJob.setTotalRooms(roomRequest.getTotalRooms());
        roomBulkJob.setHotelId(hotelId);
        roomBulkJob.setCreatedBy(String.valueOf(hotelId));
        RoomBulkJob savedRoomBulkJob = roomBulkJobRepository.save(roomBulkJob);
        asyncEventService.saveRoomDetailsAsync(savedRoomBulkJob, hotel, roomRequest);
        ResponseModel<RoomBulkJobResponse> responseModel = new ResponseModel<>();
        RoomBulkJobResponse bulkJobResponse = RoomBulkJobResponse.builder()
                .jobId(savedRoomBulkJob.getId())
                .jobStatus(savedRoomBulkJob.getStatus())
                .build();
        responseModel.setData(bulkJobResponse);
        responseModel.setMessage("Tripply is adding your room details.");
        responseModel.setStatus(HttpStatus.OK);
        log.info("RoomService: method -> rangeBulkUploadRooms() with hotelId: {} and totalRoom: {} ended", hotelId, roomRequest.getTotalRooms());
        return responseModel;
    }

    @Override
    public ResponseModel<Page<RoomBulkJobResponse>> listAllRoomBulkJobs(int page, int size, String sortBy, String sortOrder, UUID hotelId) {
        log.info("RoomService: method -> listAllRoomBulkJobs() with hotelId: {} started", hotelId);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Specification<RoomBulkJob> spec = (root, query, cb) -> {
            if (hotelId != null) {
                return cb.equal(root.get("hotelId"), hotelId);
            } else {
                return null;
            }
        };
        Page<RoomBulkJobResponse> roomBulkJobResponse = roomBulkJobRepository.findAll(spec, pageable).map(this::convertToResponse);
        ResponseModel<Page<RoomBulkJobResponse>> responseModel = new ResponseModel<>();
        responseModel.setData(roomBulkJobResponse);
        responseModel.setMessage("Room jobs data retrieved successfully.");
        responseModel.setStatus(HttpStatus.OK);
        log.info("RoomService: method -> listAllRoomBulkJobs() with hotelId: {} ended", hotelId);
        return responseModel;
    }

    private RoomBulkJobResponse convertToResponse(RoomBulkJob roomBulkJob) {
        return RoomBulkJobResponse.builder()
                .jobId(roomBulkJob.getId())
                .jobStatus(roomBulkJob.getStatus())
                .createdOn(roomBulkJob.getCreatedAt())
                .totalRooms(roomBulkJob.getTotalRooms())
                .build();
    }

}
