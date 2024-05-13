package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.Exception.ServiceCommunicationException;
import com.tripply.booking.config.WebClientService;
import com.tripply.booking.constants.enums.InvitationCategory;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.request.InviteRequest;
import com.tripply.booking.model.response.InviteResponse;
import com.tripply.booking.service.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static com.tripply.booking.constants.BookingConstant.*;

@Slf4j
@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private WebClientService webClientService;
    @Value("${app.notification.base-url}")
    private String notificationBaseUrl;

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

}
