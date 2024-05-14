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
import org.springframework.http.HttpStatus;
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
        if(checkedAlreadyInvited(hotelRequest.getManagerDetails().getEmail())) {
            throw new BadRequestException("Hotel manager already invited.");
        }
        InviteRequest inviteRequest = createHotelInviteRequest(hotelRequest);
        ResponseModel<InviteResponse> response = sendHotelInvite(inviteRequest);
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

    private ResponseModel<InviteResponse> sendHotelInvite(InviteRequest inviteRequest) {
        log.info("Begin sendInvite() for the request: {} ", inviteRequest);
        try {
            return webClientService.postWithParameterizedTypeReference(notificationBaseUrl + SEND_HOTEL_INVITE_URL,
                    inviteRequest,
                    new ParameterizedTypeReference<>() {
                    },
                    DUMMY_TOKEN);
        } catch (WebClientResponseException.BadRequest e) {
            log.error("Bad request error while sending therapist invite", e);
            throw new BadRequestException("User already registered or invite already sent");
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("Error while sending therapist invite", e);
            throw new ServiceCommunicationException("Error occurred while calling notification service");
        } catch (ResourceAccessException e) {
            log.error("Network error while sending therapist invite", e);
            throw new ServiceCommunicationException("Network error occurred while calling to notification service");
        }
    }

    private boolean checkedAlreadyInvited(String sentToEmail) {
        log.info("Begin checkedAlreadyInvitee() for the sentToEmail: {} ", sentToEmail);
        try {
            webClientService.getWithParameterizedTypeReference(notificationBaseUrl + CHECK_ALREADY_INVITED + sentToEmail,
                    new ParameterizedTypeReference<>() {
                    }, DUMMY_TOKEN);
            return true;
        } catch (Exception e) {
            log.error("Catching error for emailID: {}", sentToEmail, e);
            return false;
        }
    }

    @Override
    public ResponseModel<HotelResponse> updateHotelDetails(UUID hotelId, HotelRequest hotelRequest) {
        log.info("HotelService: Begin update hotel details with hotelId: {}", hotelId);

        try {
            Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                    () -> new DataNotFoundException("Failed to update hotel details: " + hotelId)
            );

            // Update hotel details
            hotel.setName(hotelRequest.getName());
            hotel.setAddress(hotelRequest.getAddress());
            hotel.setCity(hotelRequest.getCity());
            hotel.setStateId(hotelRequest.getStateId());
            hotel.setCountryId(hotelRequest.getCountryId());
            hotel.setDescription(hotelRequest.getDescription());
            hotel.setWebsite(hotelRequest.getWebsite());

            // Save the updated hotel
            hotel = hotelRepository.save(hotel);

            // Construct HotelResponse based on the updated hotel details
            HotelResponse hotelResponse = new HotelResponse();
            hotelResponse.setId(hotel.getId());
            hotelResponse.setName(hotel.getName());
            // Add other fields as required

            // Fetch existing manager details
            List<HotelManager> managers = hotelManagerRepository.findByHotel(hotel);
            HotelManager hotelManager = managers.stream().filter(HotelManager::getIsActive).findFirst().orElseThrow(
                    () -> new DataNotFoundException("Hotel manager details not found")
            );
            UserProfile primaryAdmin = hotelManager.getUserProfile();


            // Create the response model
            ResponseModel<HotelResponse> responseModel = new ResponseModel<>();
            responseModel.setStatus(HttpStatus.OK);
            responseModel.setMessage("Hotel details updated successfully.");
            responseModel.setTimestamp(LocalDateTime.now());
            responseModel.setData(hotelResponse);

            log.info("HotelService: End update hotel details with hotelId: {}", hotelId);
            return responseModel;
        } catch (DataNotFoundException e) {
            ResponseModel<HotelResponse> response = new ResponseModel<>();
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("Hotel not found with ID: " + hotelId);
            return response;
        } catch (Exception e) {
            ResponseModel<HotelResponse> response = new ResponseModel<>();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("An unexpected error occurred. Please try again later.");
            return response;
        }
    }

}
