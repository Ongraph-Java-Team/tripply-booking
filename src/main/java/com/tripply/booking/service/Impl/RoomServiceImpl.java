package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.config.LoggedInUser;
import com.tripply.booking.constants.enums.BookingStatus;
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
import com.tripply.booking.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoomServiceImpl implements RoomService {

    private final HotelRepository hotelRepository;
    private final AsyncEventService asyncEventService;
    private final RoomBulkJobRepository roomBulkJobRepository;
    private final AmenityRepository amenityRepository;
    private final RoomRepository roomRepository;
    private final RoomBookingRepository roomBookingRepository;
    private final RoomBookingStageRepository roomBookingStageRepository;
    private final LoggedInUser loggedInUser;
    private final static long ROOM_ONBOARD_EXPIRATION = 10L;

    public RoomServiceImpl(HotelRepository hotelRepository, AsyncEventService asyncEventService, RoomBulkJobRepository roomBulkJobRepository, AmenityRepository amenityRepository, RoomRepository roomRepository, RoomBookingRepository roomBookingRepository, RoomBookingStageRepository roomBookingStageRepository, LoggedInUser loggedInUser) {
        this.hotelRepository = hotelRepository;
        this.asyncEventService = asyncEventService;
        this.roomBulkJobRepository = roomBulkJobRepository;
        this.amenityRepository = amenityRepository;
        this.roomRepository = roomRepository;
        this.roomBookingRepository = roomBookingRepository;
        this.roomBookingStageRepository = roomBookingStageRepository;
        this.loggedInUser = loggedInUser;
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

    private List<Integer> getAvailableRooms(Hotel hotel, LocalDateTime checkInTime, LocalDateTime checkOutTime, String category, String type, Integer roomCount) {
        List<Integer> matchedRooms = roomRepository.findBySpecialFilters(hotel.getId(), category, type);
        matchedRooms.subList(roomCount, matchedRooms.size());
        List<List<Integer>> userBookedRooms = roomBookingRepository.findBookedRooms(hotel.getId(), checkInTime, checkOutTime);
        List<Integer> bookedRooms = userBookedRooms.stream().flatMap(List::stream).toList();
        matchedRooms.removeAll(bookedRooms);
        if(matchedRooms.size() < roomCount)
            throw new BadRequestException("Not enough rooms! Please change the filters of room count");
        matchedRooms.subList(roomCount, matchedRooms.size()).clear();
        return matchedRooms;
    }

    @Override
    public ResponseModel<RoomBookingResponse> bookRoom(UUID hotelId, RoomBookingRequest request) {
        log.info("RoomService: method -> bookRoom() with id: {} started", hotelId);
        UUID userId = UUID.fromString(loggedInUser.getUserId());
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new DataNotFoundException("Specified hotel details not found in our system!"));
        List<Integer> availableRooms = getAvailableRooms(hotel, DateTimeUtil.parseDateTime(request.getCheckInTime()), DateTimeUtil.parseDateTime(request.getCheckOutTime()), request.getCategory(), request.getType(), request.getRoomCount());

        RoomBooking roomBooking = new RoomBooking();
        RoomBooking savedRoomBooking = roomBookingRepository.save(roomBooking);
        RoomBookingStage roomBookingStage = new RoomBookingStage();
        roomBookingStage.setActive(true);
        roomBookingStage.setBookingId(savedRoomBooking.getId());
        roomBookingStage.setBookingStatus(BookingStatus.PENDING);
        RoomBookingStage savedRoomBookingStage = roomBookingStageRepository.save(roomBookingStage);

        savedRoomBooking.setUserId(userId);
        savedRoomBooking.setHotelId(hotelId);
        savedRoomBooking.setRoomType(request.getType());
        savedRoomBooking.setRoomCategory(request.getCategory());
        savedRoomBooking.setTotalCharge(request.getTotalCharge());
        savedRoomBooking.setRoomNumbers(availableRooms);
        savedRoomBooking.setCheckInTime(LocalDateTime.parse(request.getCheckInTime()));
        savedRoomBooking.setCheckOutTime(LocalDateTime.parse(request.getCheckOutTime()));
        savedRoomBooking = roomBookingRepository.save(savedRoomBooking);
        savedRoomBookingStage.setBookingStatus(BookingStatus.WAITING_FOR_PAYMENT);
        savedRoomBookingStage = roomBookingStageRepository.save(savedRoomBookingStage);

        ResponseModel<RoomBookingResponse> responseModel = new ResponseModel<>();
        RoomBookingResponse response = RoomBookingResponse
                .builder()
                .bookingId(savedRoomBooking.getId())
                .userId(userId)
                .hotelId(hotelId)
                .hotelName(hotel.getName())
                .roomNumbers(savedRoomBooking.getRoomNumbers())
                .roomCategory(savedRoomBooking.getRoomCategory())
                .roomType(savedRoomBooking.getRoomType())
                .checkInTime(savedRoomBooking.getCheckInTime())
                .checkOutTime(savedRoomBooking.getCheckOutTime())
                .totalRoomBooked(savedRoomBooking.getRoomNumbers().size())
                .totalCharge(savedRoomBooking.getTotalCharge())
                .bookingStatus(savedRoomBookingStage.getBookingStatus())
                .bookingInitiatedTime(savedRoomBookingStage.getCreatedOn())
                .invoiceDetails(null)
                .build();
        responseModel.setData(response);
        responseModel.setStatus(HttpStatus.OK);
        responseModel.setMessage("Room is booked successfully. Waiting for Payment");
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



    @Scheduled(fixedRate = 300000)
    public void cleanRoomBooking() {
        log.info("> SERVICE -> Clean Room Booking executing... >>>");
        List<RoomBookingStage> roomBookingStageList = roomBookingStageRepository.findAllByIsActive(true);

        for(RoomBookingStage roomBookingStage : roomBookingStageList) {
            if(LocalDateTime.now().isAfter(roomBookingStage.getCreatedOn().plusMinutes(ROOM_ONBOARD_EXPIRATION))) {
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
