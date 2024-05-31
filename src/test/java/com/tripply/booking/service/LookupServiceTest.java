package com.tripply.booking.service;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.entity.CountryCode;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.repository.LookupRepository;
import com.tripply.booking.service.Impl.LookupServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LookupServiceTest {

    @InjectMocks
    private LookupServiceImpl lookupService;

    @Mock
    private LookupRepository lookupRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCountryCode() {
        CountryCodeRequest request = setRequest();
        CountryCode countryCode = setCountryCode(request);

        when(lookupRepository.getCountryCodeByCode(request.getCode())).thenReturn(Optional.empty());
        when(lookupRepository.save(any(CountryCode.class))).thenReturn(countryCode);

       CountryCodeResponse response = lookupService.addCountryCode(request);

        assertNotNull(response);
        assertEquals("AF", response.getCode());

        verify(lookupRepository, times(1)).save(any(CountryCode.class));
        verify(lookupRepository, times(1)).getCountryCodeByCode(request.getCode());
    }

    @Test
    void testAddCountryCodeAlreadyAdded() {
        CountryCodeRequest request = setRequest();
        when(lookupRepository.getCountryCodeByCode("AF")).thenReturn(Optional.of(setCountryCode(request)));

        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> lookupService.addCountryCode(request),
                "Country Code already Added."
        );

        assertTrue(thrown.getMessage().contains("Country Code already Added."));
        verify(lookupRepository, times(1)).getCountryCodeByCode("AF");
        verify(lookupRepository, never()).save(any(CountryCode.class));
    }

    @Test
    void testGetAllCountryCode() {
        List<CountryCode> countryCodes = setListResponse();

        when(lookupRepository.findAll()).thenReturn(countryCodes);

        List<CountryCodeResponse> response = lookupService.getAllCountryCode();

        assertNotNull(response);
        verify(lookupRepository, times(1)).findAll();
    }

    private CountryCodeRequest setRequest() {
        return CountryCodeRequest.builder()
                .code("AF")
                .dialCode("+93")
                .name("Afghanistan")
                .build();
    }

    private CountryCode setCountryCode(CountryCodeRequest request) {
        return CountryCode.builder()
                .code(request.getCode())
                .dialCode(request.getDialCode())
                .name(request.getName())
                .build();
    }

    private List<CountryCode> setListResponse() {
        return Stream.of(
                        new CountryCode(1L, "AF", "+93", "Afghanistan"),
                        new CountryCode(2L, "US", "+1", "United States"),
                        new CountryCode(3L, "IN", "+91", "India")
                )
                .map(data -> CountryCode.builder()
                        .code(data.getCode())
                        .dialCode(data.getDialCode())
                        .name(data.getName())
                        .build())
                .collect(Collectors.toList());
    }
}
