package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.Exception.BookingExceptionHandler;
import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.constants.enums.JobStatus;
import com.tripply.booking.entity.*;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.RoomBookingRequest;
import com.tripply.booking.model.request.RoomRequest;
import com.tripply.booking.model.response.RoomBookingResponse;
import com.tripply.booking.model.response.RoomBulkJobResponse;
import com.tripply.booking.model.response.RoomResponse;
import com.tripply.booking.repository.*;
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
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final HotelRepository hotelRepository;
    private final AsyncEventService asyncEventService;
    private final RoomBulkJobRepository roomBulkJobRepository;
    private final AmenityRepository amenityRepository;
    private final RoomRepository roomRepository;
    private final UserProfileRepository userProfileRepository;
    private final RoomBookingRepository roomBookingRepository;

    public RoomServiceImpl(HotelRepository hotelRepository, AsyncEventService asyncEventService, RoomBulkJobRepository roomBulkJobRepository, AmenityRepository amenityRepository, RoomRepository roomRepository, UserProfileRepository userProfileRepository, RoomBookingRepository roomBookingRepository) {
        this.hotelRepository = hotelRepository;
        this.asyncEventService = asyncEventService;
        this.roomBulkJobRepository = roomBulkJobRepository;
        this.amenityRepository = amenityRepository;
        this.roomRepository = roomRepository;
        this.userProfileRepository = userProfileRepository;
        this.roomBookingRepository = roomBookingRepository;
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
        Page<RoomBulkJobResponse> roomBulkJobResponse = roomBulkJobRepository.findAll(spec, pageable).map(this::convertToJobResponse);
        ResponseModel<Page<RoomBulkJobResponse>> responseModel = new ResponseModel<>();
        responseModel.setData(roomBulkJobResponse);
        responseModel.setMessage("Room jobs data retrieved successfully.");
        responseModel.setStatus(HttpStatus.OK);
        log.info("RoomService: method -> listAllRoomBulkJobs() with hotelId: {} ended", hotelId);
        return responseModel;
    }

    @Override
    public ResponseModel<Page<RoomResponse>> listAllRooms(int page, int size, String sortBy, String sortOrder, UUID hotelId) {
        log.info("RoomService: method -> listAllRooms() with hotelId: {} started", hotelId);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Specification<RoomBulkJob> spec = (root, query, cb) -> {
            if (Objects.nonNull(hotelId)) {
                Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                        () -> new DataNotFoundException("Specified hotel details not found.")
                );
                return cb.equal(root.get("hotel"), hotel);
            } else {
                return null;
            }
        };
        Page<RoomResponse> roomBulkJobResponse = roomRepository.findAll(spec, pageable).map(this::convertToRoomResponse);
        ResponseModel<Page<RoomResponse>> responseModel = new ResponseModel<>();
        responseModel.setData(roomBulkJobResponse);
        responseModel.setMessage("Room jobs data retrieved successfully.");
        responseModel.setStatus(HttpStatus.OK);
        log.info("RoomService: method -> listAllRooms() with hotelId: {} ended", hotelId);
        return responseModel;
    }

    @Override
    public ResponseModel<Room> getRoomDetailsById(Long id) {
        log.info("RoomService: method -> getRoomDetailsById() with id: {} started", id);
        Room room = roomRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Specified room details not found in our system.")
        );
        ResponseModel<Room> responseModel = new ResponseModel<>();
        responseModel.setData(room);
        responseModel.setMessage("Room details retrieved successfully.");
        log.info("RoomService: method -> getRoomDetailsById() with id: {} ended", id);
        responseModel.setStatus(HttpStatus.OK);
        return responseModel;
    }

    @Override
    public ResponseModel<RoomBookingResponse> bookRoom(UUID hotelId, RoomBookingRequest request) {
        log.info("RoomService: method -> bookRoom() with id: {} started", hotelId);
        UserProfile user = userProfileRepository.findById(UUID.fromString(request.getUserId())).orElseThrow(() -> new DataNotFoundException("Specified user details not found in our system."));
        List<Integer> availableRooms = roomRepository.findBySpecialFilters(String.valueOf(hotelId), request.getCategory(), request.getType(), request.getCheckInTime(), request.getCheckOutTime());
        if(availableRooms.size() < request.getRoomCount())
            throw new BadRequestException("Not enough rooms!");
        availableRooms.subList(request.getRoomCount(), availableRooms.size());

        RoomBooking roomBooking = new RoomBooking();
        roomBooking.setUser(user);
        roomBooking.setRoomNumbers((Integer[]) availableRooms.toArray());
        roomBooking.setCheckInTime(LocalDateTime.parse(request.getCheckInTime()));
        roomBooking.setCheckOutTime(LocalDateTime.parse(request.getCheckOutTime()));
        RoomBooking savedRoomBooking = roomBookingRepository.save(roomBooking);
        ResponseModel<RoomBookingResponse> responseModel = new ResponseModel<>();
        RoomBookingResponse response = new RoomBookingResponse();
        response.setRoomCategory(savedRoomBooking.toString());
        responseModel.setData(response);
        responseModel.setStatus(HttpStatus.OK);
        responseModel.setMessage("Room is booked successfully");
        log.info("RoomService: method -> bookRoom() with id: {} ended", hotelId);
        return responseModel;
    }

    private RoomBulkJobResponse convertToJobResponse(RoomBulkJob roomBulkJob) {
        return RoomBulkJobResponse.builder()
                .jobId(roomBulkJob.getId())
                .jobStatus(roomBulkJob.getStatus())
                .createdOn(roomBulkJob.getCreatedAt())
                .totalRooms(roomBulkJob.getTotalRooms())
                .build();
    }

    private RoomResponse convertToRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .price(room.getPrice())
                .type(room.getType())
                .category(room.getCategory())
                .floor(room.getFloor())
                .createdAt(room.getCreatedAt())
                .description(room.getDescription())
                .howToReach(room.getHowToReach())
                .build();
    }

}
