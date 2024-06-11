package com.tripply.booking.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.Exception.DataNotFoundException;
import com.tripply.booking.Exception.ServiceCommunicationException;
import com.tripply.booking.config.WebClientService;
import com.tripply.booking.constants.enums.InvitationCategory;
import com.tripply.booking.entity.BaseEntity;
import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.HotelManager;
import com.tripply.booking.entity.UserProfile;
import com.tripply.booking.model.HotelManagerPersonalInfo;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.request.InviteRequest;
import com.tripply.booking.model.response.HotelResponse;
import com.tripply.booking.model.response.InviteResponse;
import com.tripply.booking.repository.HotelManagerRepository;
import com.tripply.booking.repository.HotelRepository;
import com.tripply.booking.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.tripply.booking.constants.BookingConstant.*;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    @Autowired
    private WebClientService webClientService;
    @Value("${app.notification.base-url}")
    private String notificationBaseUrl;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelManagerRepository hotelManagerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResponseModel<InviteResponse> createHotel(HotelRequest hotelRequest) {
        log.info("Start HotelService createHotel(): {}", hotelRequest.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = (String) authentication.getCredentials();
        if(checkedAlreadyInvited(hotelRequest.getManagerDetails().getEmail(), token)) {
            throw new BadRequestException("Hotel manager already invited.");
        }
        InviteRequest inviteRequest = createHotelInviteRequest(hotelRequest);
        ResponseModel<InviteResponse> response = sendHotelInvite(inviteRequest, token);
        log.info("End HotelService createHotel(): {}", hotelRequest.getName());
        return response;
    }

    @Override
    public ResponseModel<HotelResponse> getHotelById(UUID hotelId) {
        log.info("Start HotelService getHotelById() with id: {}", hotelId);
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new DataNotFoundException("Hotel details not found with id: " + hotelId)
        );
        HotelResponse hotelResponse = new HotelResponse();
        BeanUtils.copyProperties(hotel, hotelResponse);
        List<HotelManager> managers = hotelManagerRepository.findByHotel(hotel);
        HotelManager hotelManager = managers.stream().filter(HotelManager::getIsActive).findFirst().orElseThrow(
                () -> new DataNotFoundException("Hotel manager details not found")
        );
        UserProfile primaryAdmin = hotelManager.getUserProfile();
        if(Objects.nonNull(primaryAdmin)) {
            HotelManagerPersonalInfo personalInfo = objectMapper.convertValue(primaryAdmin.getPersonalInfo(), HotelManagerPersonalInfo.class);
            hotelResponse.setAdminName(
                String.join(" ",
                    primaryAdmin.getFirstName(),
                    primaryAdmin.getLastName()
                )
            );
            hotelResponse.setAdminEmail(personalInfo.getEmail());
            hotelResponse.setPhoneNumber(personalInfo.getPhoneNumber());
            hotelResponse.setCountryCode(personalInfo.getCountryCode());
        }
        ResponseModel<HotelResponse> response = new ResponseModel<>();
        response.setMessage("Hotel details retrieved successfully.");
        response.setStatus(HttpStatus.OK);
        response.setData(hotelResponse);
        log.info("End HotelService getHotelById() with id: {}", hotelId);
        return response;
    }

    @Override
    public ResponseModel<Page<HotelResponse>> getAllHotels(String sortBy, String sortOrder, int page, int size) {
        log.info("Start HotelService getAllHotels() with pageSize: {}", page);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<HotelResponse> hotelResponses = hotelRepository.findAllByFilters(pageable);
        ResponseModel<Page<HotelResponse>> response = new ResponseModel<>();
        response.setData(hotelResponses);
        response.setMessage("All hotels data retrieved successfully");
        response.setStatus(HttpStatus.OK);
        log.info("End HotelService getAllHotels() with pageSize: {}", page);
        return response;
    }

    private InviteRequest createHotelInviteRequest(HotelRequest hotelRequest) {
        InviteRequest inviteRequest = new InviteRequest();
        inviteRequest.setCategory(InvitationCategory.HOTEL);
        inviteRequest.setSendToName(
                String.join(" ",
                        hotelRequest.getManagerDetails().getFirstName(),
                        hotelRequest.getManagerDetails().getLastName()
                )
        );
        inviteRequest.setSentToEmail(hotelRequest.getManagerDetails().getEmail());
        inviteRequest.setHotelRequestBean(hotelRequest);
        return inviteRequest;
    }

    private ResponseModel<InviteResponse> sendHotelInvite(InviteRequest inviteRequest, String token) {
        log.info("Begin sendInvite() for the request: {} ", inviteRequest);
        try {
            return webClientService.postWithParameterizedTypeReference(notificationBaseUrl + SEND_HOTEL_INVITE_URL,
                    inviteRequest,
                    new ParameterizedTypeReference<>() {
                    },
                    token);
        } catch (WebClientResponseException.BadRequest e) {
            log.error("Bad request error while sending hotel invite", e);
            throw new BadRequestException("User already registered or invite already sent");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error while sending hotel invite", e);
            throw new ServiceCommunicationException("Error occurred while calling notification service");
        } catch (ResourceAccessException e) {
            log.error("Network error while sending hotel invite", e);
            throw new ServiceCommunicationException("Network error occurred while calling to notification service");
        }
    }

    private boolean checkedAlreadyInvited(String sentToEmail, String token) {
        log.info("Begin checkedAlreadyInvitee() for the sentToEmail: {} ", sentToEmail);
        try {
            webClientService.getWithParameterizedTypeReference(notificationBaseUrl + CHECK_ALREADY_INVITED + sentToEmail,
                    new ParameterizedTypeReference<>() {
                    }, token);
            return true;
        } catch (Exception e) {
            log.error("Catching error for emailID: {}", sentToEmail, e);
            return false;
        }
    }

    @Override
    public ResponseModel<HotelResponse> updateHotelDetails(UUID hotelId, HotelRequest hotelRequest) {
        log.info("HotelService: Begin update hotel details with hotelId: {}", hotelId);

        ResponseModel<HotelResponse> responseModel = new ResponseModel<>();
        responseModel.setTimestamp(LocalDateTime.now());

        try {
            Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                    () -> new DataNotFoundException("Failed to update hotel details: " + hotelId)
            );


            BeanUtils.copyProperties(hotelRequest, hotel);

            hotel = hotelRepository.save(hotel);

            HotelResponse hotelResponse = new HotelResponse();
            BeanUtils.copyProperties(hotel, hotelResponse);

            List<HotelManager> managers = hotelManagerRepository.findByHotel(hotel);
            HotelManager hotelManager = managers.stream().filter(HotelManager::getIsActive).findFirst().orElseThrow(
                    () -> new DataNotFoundException("Hotel manager details not found")
            );
            UserProfile primaryAdmin = hotelManager.getUserProfile();

            if (Objects.nonNull(primaryAdmin)) {
                HotelManagerPersonalInfo personalInfo = objectMapper.convertValue(primaryAdmin.getPersonalInfo(), HotelManagerPersonalInfo.class);
                hotelResponse.setAdminName(
                        String.join(" ",
                                primaryAdmin.getFirstName(),
                                primaryAdmin.getLastName()
                        )
                );
                hotelResponse.setAdminEmail(personalInfo.getEmail());
                hotelResponse.setPhoneNumber(personalInfo.getPhoneNumber());
                hotelResponse.setCountryCode(personalInfo.getCountryCode());
            }

            responseModel.setStatus(HttpStatus.OK);
            responseModel.setMessage("Hotel details updated successfully.");
            responseModel.setData(hotelResponse);

            log.info("HotelService: End update hotel details with hotelId: {}", hotelId);
            return responseModel;
        } catch (DataNotFoundException e) {
            responseModel.setStatus(HttpStatus.NOT_FOUND);
            responseModel.setMessage("Hotel not found with ID: " + hotelId);
            return responseModel;
        } catch (Exception e) {
            responseModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            responseModel.setMessage("An unexpected error occurred. Please try again later.");
            return responseModel;
        }
    }



}
