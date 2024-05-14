package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.config.WebClientService;
import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.HotelRating;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.RatingRequest;
import com.tripply.booking.model.response.RatingResponse;
import com.tripply.booking.repository.HotelRatingRepository;
import com.tripply.booking.repository.HotelRepository;
import com.tripply.booking.repository.UserProfileRepository;
import com.tripply.booking.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tripply.booking.constants.BookingConstant.CHECK_USER_ALREADY_EXIST;
import static com.tripply.booking.constants.BookingConstant.DUMMY_TOKEN;

@Slf4j
@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private WebClientService webClientService;
    @Value("${app.auth.base-url}")
    private String authBaseUrl;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelRatingRepository hotelRatingRepository;

    @Override
    public ResponseModel<RatingResponse> addRating(RatingRequest ratingRequest) {
        log.info("Start RatingService addRating() for user with id: {}", ratingRequest.getUserId());
        if(checkedUserAlreadyPresent(ratingRequest.getUserId())) {
            throw new DataNotFoundException("User not found with id: " + ratingRequest.getUserId());
        }
        Hotel hotel = hotelRepository.findById(ratingRequest.getUserId()).orElseThrow(
                () -> new DataNotFoundException("Hotel not found with id: " + ratingRequest.getHotelId())
        );
        HotelRating hotelRating = new HotelRating();
        hotelRating.setRating(ratingRequest.getRating());
        hotelRating.setComment(ratingRequest.getComment());
        hotelRating.setCreatedBy(ratingRequest.getUserId());
        hotelRating.setUserId(ratingRequest.getUserId());
        hotelRating.setHotel(hotel);
        HotelRating savedRating = hotelRatingRepository.save(hotelRating);
        RatingResponse ratingResponse = new RatingResponse();
        BeanUtils.copyProperties(ratingRequest, ratingResponse);
        ratingResponse.setId(savedRating.getId());
        ResponseModel<RatingResponse> response = new ResponseModel<>();
        response.setData(ratingResponse);
        response.setStatus(HttpStatus.CREATED);
        response.setMessage("Rating added successfully");
        log.info("End RatingService addRating() for user with id: {}", ratingRequest.getUserId());
        return response;
    }

    private boolean checkedUserAlreadyPresent(UUID userId) {
        log.info("Begin checkedUserAlreadyPresent() for the userId: {} ", userId);
        try {
            webClientService.getWithParameterizedTypeReference(authBaseUrl + CHECK_USER_ALREADY_EXIST + userId,
                    new ParameterizedTypeReference<>() {
                    }, DUMMY_TOKEN);
            return true;
        } catch (Exception e) {
            log.error("Catching error for userId: {}", userId, e);
            return false;
        }
    }

}
