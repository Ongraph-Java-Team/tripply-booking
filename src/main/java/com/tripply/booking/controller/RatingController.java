package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.TopRatedHotelProjection;
import com.tripply.booking.model.request.RatingRequest;
import com.tripply.booking.model.response.RatingResponse;
import com.tripply.booking.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @Operation(summary = "Add rating",
            description = "This API will add rating of a hotel and will throw error if hotel or user does not exist.")
    @PostMapping
    public ResponseEntity<ResponseModel<RatingResponse>> addRating(@Valid @RequestBody RatingRequest ratingRequest) {
        log.info("Start Endpoint: / addRating() for user with id: {}", ratingRequest.getUserId());
        ResponseModel<RatingResponse> response = ratingService.addRating(ratingRequest);
        log.info("End Endpoint: / addRating() for user with id: {}", ratingRequest.getUserId());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get All ratings",
            description = "This API will get all ratings of a particular hotel.")
    @GetMapping
    public ResponseEntity<ResponseModel<List<RatingResponse>>> getAllRatings(
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID hotelId,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @RequestParam(defaultValue = "createdOn") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Start Endpoint: / getAllRatings() with pageSize: {} and pageNumber: {}", size, page);
        ResponseModel<List<RatingResponse>> response = ratingService.getAllRatings(userId, hotelId, minRating, maxRating, sortBy, sortOrder, page, size);
        log.info("End Endpoint: / getAllRatings() with pageSize: {} and pageNumber: {}", size, page);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get top rated hotels",
            description = "This API will give all top rated hotels.")
    @GetMapping("/top-rated-hotels")
    public ResponseEntity<ResponseModel<List<TopRatedHotelProjection>>> getTopRatedHotels(@RequestParam(value = "top", defaultValue = "5") Integer top) {
        log.info("Start Endpoint: /top-rated-hotels getTopRatedHotels() with top: {}", top);
        ResponseModel<List<TopRatedHotelProjection>> response = ratingService.getTopRatedHotels(top);
        log.info("End Endpoint: /top-rated-hotels getTopRatedHotels() with top: {}", top);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
