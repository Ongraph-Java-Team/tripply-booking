package com.tripply.booking.controller;

import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.RatingRequest;
import com.tripply.booking.model.response.InviteResponse;
import com.tripply.booking.model.response.RatingResponse;
import com.tripply.booking.service.RatingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping()
    public ResponseEntity<ResponseModel<RatingResponse>> addRating(@Valid @RequestBody RatingRequest ratingRequest) {
        log.info("Start Endpoint: / addRating() for user with id: {}", ratingRequest.getUserId());
        ResponseModel<RatingResponse> response = ratingService.addRating(ratingRequest);
        log.info("End Endpoint: / addRating() for user with id: {}", ratingRequest.getUserId());
        return ResponseEntity.ok(response);
    }

}
