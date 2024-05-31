package com.tripply.booking.service.Impl;

import com.tripply.booking.constants.enums.JobStatus;
import com.tripply.booking.entity.Amenity;
import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.Room;
import com.tripply.booking.entity.RoomBulkJob;
import com.tripply.booking.model.request.RoomDetailsRequest;
import com.tripply.booking.model.request.RoomRequest;
import com.tripply.booking.model.response.RoomUploadResponse;
import com.tripply.booking.repository.AmenityRepository;
import com.tripply.booking.repository.RoomBulkJobRepository;
import com.tripply.booking.repository.RoomRepository;
import com.tripply.booking.service.AsyncEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AsyncEventServiceImpl implements AsyncEventService {

    private final RoomBulkJobRepository roomBulkJobRepository;
    private final AmenityRepository amenityRepository;
    private final RoomRepository roomRepository;

    public AsyncEventServiceImpl(RoomBulkJobRepository roomBulkJobRepository, AmenityRepository amenityRepository, RoomRepository roomRepository) {
        this.roomBulkJobRepository = roomBulkJobRepository;
        this.amenityRepository = amenityRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void saveRoomDetailsAsync(RoomBulkJob savedRoomBulkJob, Hotel hotel, RoomRequest roomRequest) {
        log.info("AsyncEventService: method -> saveRoomDetailsAsync() started with room data of hotel: {}", hotel.getName());
        savedRoomBulkJob.setStatus(JobStatus.IN_PROGRESS);
        savedRoomBulkJob.setUpdatedAt(LocalDateTime.now());
        savedRoomBulkJob = roomBulkJobRepository.save(savedRoomBulkJob);
        List<Integer> existingRoomNumbers = roomRepository.findRoomNumbersByHotelId(hotel.getId());
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<RoomDetailsRequest> roomDetailsList = roomRequest.getRoomDetails();
        List<CompletableFuture<List<Room>>> futures = new ArrayList<>();
        List<RoomUploadResponse> roomResponses = new CopyOnWriteArrayList<>();
        Map<String, Set<Amenity>> amenityMap = new HashMap<>();
        for(RoomDetailsRequest roomDetails: roomDetailsList) {
            amenityMap.put(roomDetails.getRoomRange(), amenityRepository.findByIdIn(roomDetails.getAmenities()));
        }
        for (RoomDetailsRequest roomDetails : roomDetailsList) {
            CompletableFuture<List<Room>> future = CompletableFuture.supplyAsync(() -> {
                List<Room> rooms = new ArrayList<>();
                try {
                    Set<Amenity> amenities = amenityMap.get(roomDetails.getRoomRange());
                    String[] range = roomDetails.getRoomRange().split("-");
                    int start = Integer.parseInt(range[0]);
                    int end = Integer.parseInt(range[1]);
                    for(int idx=start; idx<=end; idx++) {
                        RoomUploadResponse roomUploadResponse = new RoomUploadResponse();
                        if(!existingRoomNumbers.contains(idx)) {
                            Room room = new Room();
                            room.setFloor(roomDetails.getFloor());
                            room.setRoomNumber(idx);
                            room.setCategory(roomDetails.getCategory());
                            room.setHowToReach(roomDetails.getHowToReach());
                            room.setPrice(roomDetails.getPrice());
                            room.setExtraAmenties(roomDetails.getExtraAmenities());
                            room.setCreatedBy(String.valueOf(hotel.getId()));
                            room.setHotel(hotel);
                            room.setAmenities(amenities);
                            rooms.add(room);
                            roomUploadResponse.setRoomNumber(idx);
                            roomUploadResponse.setMessage("Room added successfully");
                            roomUploadResponse.setStatus(201);
                        } else {
                            roomUploadResponse.setRoomNumber(idx);
                            roomUploadResponse.setMessage("Room already exists in our system");
                            roomUploadResponse.setStatus(302);
                        }
                        roomResponses.add(roomUploadResponse);
                    }
                } catch (Exception e) {
                    log.error("Exception occurred while processing room: {}", roomDetails.getRoomRange(), e);
                }
                return rooms;
            }, executorService).exceptionally(ex -> {
                log.error("Exception occurred while processing room asynchronously: {}", roomDetails.getRoomRange(), ex);
                return new ArrayList<>();
            });
            futures.add(future);
        }

        try {
            List<Room> allRooms = futures.stream()
                    .map(CompletableFuture::join)
                    .flatMap(List::stream)
                    .toList();

            roomRepository.saveAll(allRooms);

            Map<Integer, List<RoomUploadResponse>> responseMap = roomResponses.stream().collect(Collectors.groupingBy(RoomUploadResponse::getStatus));

            int originalSize = roomResponses.size();
            int successCount = (Objects.nonNull(responseMap.get(201))) ? responseMap.get(201).size() : 0;
            int failedCount = (Objects.nonNull(responseMap.get(302))) ? responseMap.get(302).size() : 0;

            if (successCount == originalSize) {
                savedRoomBulkJob.setStatus(JobStatus.SUCCESS);
            } else if (successCount > 0 && failedCount > 0) {
                savedRoomBulkJob.setStatus(JobStatus.PARTIALLY_SUCCESS);
            } else {
                savedRoomBulkJob.setStatus(JobStatus.FAILED);
            }
            savedRoomBulkJob.setRoomUploadResponses(roomResponses);
        } catch (Exception e) {
            log.error("Exception occurred while adding room details", e);
            savedRoomBulkJob.setStatus(JobStatus.FAILED);
        }
        savedRoomBulkJob.setUpdatedAt(LocalDateTime.now());
        roomBulkJobRepository.save(savedRoomBulkJob);
        log.info("AsyncEventService: method -> saveRoomDetailsAsync() ended with room data of hotel: {}", hotel.getName());
    }

}
