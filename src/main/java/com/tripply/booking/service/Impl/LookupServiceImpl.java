package com.tripply.booking.service.Impl;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.entity.CountryCode;
import com.tripply.booking.entity.State;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.request.StateRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.model.response.StateResponse;
import com.tripply.booking.repository.LookupRepository;
import com.tripply.booking.repository.StateRepository;
import com.tripply.booking.service.LookupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LookupServiceImpl implements LookupService {

    private final LookupRepository lookupRepository;

    private final StateRepository stateRepository;

    public LookupServiceImpl(LookupRepository lookupRepository, StateRepository stateRepository) {
        this.lookupRepository = lookupRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public CountryCodeResponse addCountryCode(CountryCodeRequest request) {
        Optional<CountryCode> countryCodeOptional = lookupRepository.getCountryCodeByCode(request.getCode());
        if (countryCodeOptional.isPresent()) {
            throw new BadRequestException("Country Code already Added.");
        }
        CountryCode countryCode = setCountryCode(request);
        countryCode = lookupRepository.save(countryCode);
        CountryCodeResponse countryCodeResponse = setCountryCodeToResponse(countryCode);
        return countryCodeResponse;
    }

    @Override
    public List<CountryCodeResponse> getAllCountryCode() {
        List<CountryCode> countryCodeList = lookupRepository.findAll();
        List<CountryCodeResponse> countryCodeResponseList = countryCodeList.stream()
                .map(this::setCountryCodeToResponse)
                .collect(Collectors.toList());
        return countryCodeResponseList;
    }

    @Override
    public StateResponse addState(StateRequest request) {
        State stateOptional = stateRepository.getStateByStateName(request.getStateName());
        if (stateOptional!=null) {
            throw new BadRequestException("State already Added.");
        }
        State state = State.builder().stateName(request.getStateName()).countryId(request.getCountryId()).build();
        state = stateRepository.save(state);
        StateResponse response = StateResponse.builder()
                .stateName(state.getStateName())
                .countryId(state.getCountryId())
                .id(state.getId()).build();
        return response;
    }

    @Override
    public List<StateResponse> getAllStateByCountryId(Long countryId) {
        List<State> stateList = stateRepository.findAllByCountryId(countryId);
        List<StateResponse> response = stateList.stream().map(state -> StateResponse.builder()
                .stateName(state.getStateName())
                .countryId(state.getCountryId())
                .id(state.getId()).build())
                .collect(Collectors.toList());
        return response;
    }

    private CountryCode setCountryCode(CountryCodeRequest request) {
        CountryCode countryCode = new CountryCode();
        countryCode.setDialCode(request.getDialCode());
        countryCode.setCode(request.getCode());
        countryCode.setName(request.getName());
        return countryCode;
    }

    private CountryCodeResponse setCountryCodeToResponse(CountryCode countryCode) {
        return CountryCodeResponse.builder()
                .code(countryCode.getCode())
                .id(countryCode.getId())
                .name(countryCode.getName())
                .dialCode(countryCode.getDialCode())
                .build();
    }

}
