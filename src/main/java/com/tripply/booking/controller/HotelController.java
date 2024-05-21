package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.response.HotelResponse;
import com.tripply.booking.model.response.InviteResponse;
import com.tripply.booking.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hotels")
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Operation(summary = "Add Hotel",
            description = "This API will add hotel and will throw exception if it already exists.")
    @PostMapping
    public ResponseEntity<ResponseModel<InviteResponse>> addHotel(@Valid @RequestBody HotelRequest hotelRequest) {
        log.info("Start Endpoint: / addHotel(): {}", hotelRequest.getName());
        ResponseModel<InviteResponse> response = hotelService.createHotel(hotelRequest);
        log.info("End Endpoint: / addHotel(): {}", hotelRequest.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<HotelResponse>> getHotelById(@PathVariable("id") UUID hotelId) {
        log.info("Start Endpoint: /{id} getHotelById() with id: {}", hotelId);
        ResponseModel<HotelResponse> response = hotelService.getHotelById(hotelId);
        log.info("End Endpoint: /{id} getHotelById() with id: {}", hotelId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseModel<Page<HotelResponse>>> getAllHotels(@RequestParam(defaultValue = "createdOn") String sortBy,
                                                                           @RequestParam(defaultValue = "desc") String sortOrder,
                                                                           @RequestParam(defaultValue = "0") int page,
                                                                           @RequestParam(defaultValue = "10") int size) {
        log.info("Start Endpoint: / getAllHotels() with pageSize: {}", page);
        ResponseModel<Page<HotelResponse>> response = hotelService.getAllHotels(sortBy, sortOrder, page, size);
        log.info("End Endpoint: / getAllHotels() with pageSize: {}", page);
        return ResponseEntity.ok(response);
    }

}
