package com.tripply.booking.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripply.booking.entity.Hotel;
import com.tripply.booking.entity.HotelManager;
import com.tripply.booking.entity.UserProfile;
import com.tripply.booking.model.HotelManagerPersonalInfo;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.HotelRequest;
import com.tripply.booking.model.request.ManagerDetailsRequest;
import com.tripply.booking.model.response.HotelResponse;
import com.tripply.booking.repository.HotelManagerRepository;
import com.tripply.booking.repository.HotelRepository;
import com.tripply.booking.repository.UserProfileRepository;
import com.tripply.booking.service.OnboardService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OnboardServiceImpl implements OnboardService {

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private HotelManagerRepository hotelManagerRepository;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ResponseModel<HotelResponse> onboardHotel(HotelRequest hotelRequest) {
        log.info("Start HotelService onboardHotel(): {}", hotelRequest.getName());
        Hotel hotel = new Hotel();
        BeanUtils.copyProperties(hotelRequest, hotel);
        Hotel savedHotel = hotelRepository.save(hotel);
        saveHotelUser(savedHotel, hotelRequest.getManagerDetails());
        ResponseModel<HotelResponse> response = new ResponseModel<>();
        HotelResponse hotelResponse = new HotelResponse();
        hotelResponse.setId(savedHotel.getId());
        hotelResponse.setName(savedHotel.getName());
        hotelResponse.setRegistrationNumber(savedHotel.getRegistrationNumber());
        hotelResponse.setAdminName(String.join(" ",
                hotelRequest.getManagerDetails().getFirstName(),
                hotelRequest.getManagerDetails().getLastName()
        ));
        hotelResponse.setAdminEmail(hotelRequest.getManagerDetails().getEmail());
        hotelResponse.setPhoneNumber(String.join("",
                hotelRequest.getManagerDetails().getCountryCode(),
                hotelRequest.getManagerDetails().getPhoneNumber()
        ));
        response.setData(hotelResponse);
        response.setMessage("Hotel onboarded successfully");
        response.setStatus(HttpStatus.CREATED);
        log.info("End HotelService onboardHotel(): {}", hotelRequest.getName());
        return response;
    }

    private void saveHotelUser(Hotel savedHotel, ManagerDetailsRequest managerDetails) {
        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(managerDetails.getFirstName());
        userProfile.setLastName(managerDetails.getLastName());
        userProfile.setUserId(managerDetails.getUserId());
        HotelManagerPersonalInfo personalInfo = new HotelManagerPersonalInfo();
        BeanUtils.copyProperties(managerDetails, personalInfo);
        JSONObject jsonObject = objectMapper.convertValue(personalInfo, JSONObject.class);
        userProfile.setPersonalInfo(jsonObject);
        UserProfile savedUserProfile = userProfileRepository.save(userProfile);
        HotelManager hotelManager = new HotelManager();
        hotelManager.setHotel(savedHotel);
        hotelManager.setUserProfile(savedUserProfile);
        hotelManager.setIsActive(true);
        hotelManagerRepository.save(hotelManager);
    }

}
