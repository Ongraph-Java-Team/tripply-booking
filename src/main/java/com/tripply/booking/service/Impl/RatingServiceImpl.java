package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.config.WebClientService;
import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.HotelRating;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.TopRatedHotelProjection;
import com.tripply.booking.model.request.RatingRequest;
import com.tripply.booking.model.response.RatingResponse;
import com.tripply.booking.repository.HotelRatingRepository;
import com.tripply.booking.repository.HotelRepository;
import com.tripply.booking.repository.UserProfileRepository;
import com.tripply.booking.service.RatingService;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Hotel hotel = hotelRepository.findById(ratingRequest.getHotelId()).orElseThrow(
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

    @Override
    public ResponseModel<List<RatingResponse>> getAllRatings(UUID userId, UUID hotelId, Integer minRating, Integer maxRating, String sortBy, String sortOrder, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Specification<HotelRating> specification = buildSpecification(userId, hotelId, minRating, maxRating);

        Page<HotelRating> ratingsPage = hotelRatingRepository.findAll(specification, pageable);
        List<RatingResponse> ratingResponses = ratingsPage.getContent().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        ResponseModel<List<RatingResponse>> response = new ResponseModel<>();
        response.setData(ratingResponses);
        response.setStatus(HttpStatus.OK);
        response.setMessage("Ratings retrieved successfully");
        return response;
    }

    private Specification<HotelRating> buildSpecification(UUID userId, UUID hotelId, Integer minRating, Integer maxRating) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("userId"), userId));
            }
            if (hotelId != null) {
                predicates.add(criteriaBuilder.equal(root.get("hotel").get("id"), hotelId));
            }
            if (minRating != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), minRating));
            }
            if (maxRating != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("rating"), maxRating));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private RatingResponse convertToResponse(HotelRating hotelRating) {
        RatingResponse ratingResponse = new RatingResponse();
        BeanUtils.copyProperties(hotelRating, ratingResponse);
        ratingResponse.setHotelId(hotelRating.getHotel().getId());
        ratingResponse.setComments(hotelRating.getComment());
        return ratingResponse;
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

    @Override
    public ResponseModel<List<TopRatedHotelProjection>> getTopRatedHotels(Integer top) {
        List<TopRatedHotelProjection> topRatedHotels = hotelRatingRepository.findTopRatedHotels(PageRequest.of(0, top)).getContent();
        ResponseModel<List<TopRatedHotelProjection>> response = new ResponseModel<>();
        response.setData(topRatedHotels);
        response.setStatus(HttpStatus.OK);
        response.setMessage("Top "+top+" rated hotels retrieved successfully");
        return response;
    }

}
