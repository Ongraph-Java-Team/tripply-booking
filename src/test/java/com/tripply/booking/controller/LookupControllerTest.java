package com.tripply.booking.controller;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.service.LookupService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LookupControllerTest {

    @InjectMocks
    private LookupController countryCodeController;

    @Mock
    private LookupService lookupService;

    @Test
    void testAddCountryCode() {
        CountryCodeRequest request = setRequest();
        CountryCodeResponse response = setResponse();
        when(lookupService.addCountryCode(request)).thenReturn(response);

        ResponseEntity<ResponseModel<CountryCodeResponse>> result = countryCodeController.addCountryCode(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Country Code added successfully", result.getBody().getMessage());
        assertEquals(HttpStatus.CREATED, result.getBody().getStatus());
        assertEquals("AF", result.getBody().getData().getCode());

        verify(lookupService, times(1)).addCountryCode(request);
    }

    @Test
    void testAddCountryCodeAlreadyAdded() {
        CountryCodeRequest request = setRequest();

        when(lookupService.addCountryCode(request)).thenThrow(new BadRequestException("Country Code already Added."));

        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> countryCodeController.addCountryCode(request),
                "Country Code already Added."
        );

        assertTrue(thrown.getMessage().contains("Country Code already Added."));
        verify(lookupService, times(1)).addCountryCode(request);
    }


    @Test
    void testGetAllCountryCode() {
        List<CountryCodeResponse> response = setListResponse();
        when(lookupService.getAllCountryCode()).thenReturn(response);

        ResponseEntity<ResponseModel<List<CountryCodeResponse>>> result = countryCodeController.getAllCountryCode();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Country Codes retrieved successfully", result.getBody().getMessage());
        assertEquals(HttpStatus.FOUND, result.getBody().getStatus());

        verify(lookupService, times(1)).getAllCountryCode();
    }

    private List<CountryCodeResponse> setListResponse() {
        return Stream.of(
                        new CountryCodeResponse(1L, "AF", "+93", "Afghanistan"),
                        new CountryCodeResponse(2L, "US", "+1", "United States"),
                        new CountryCodeResponse(3L, "IN", "+91", "India")
                )
                .map(data -> CountryCodeResponse.builder()
                        .code(data.getCode())
                        .dialCode(data.getDialCode())
                        .name(data.getName())
                        .build())
                .collect(Collectors.toList());
    }

    private CountryCodeResponse setResponse() {
        return CountryCodeResponse.builder()
                .code("AF")
                .dialCode("+93")
                .name("Afghanistan")
                .build();
    }

    private CountryCodeRequest setRequest() {
        return CountryCodeRequest.builder()
                .code("AF")
                .dialCode("+93")
                .name("Afghanistan")
                .build();
    }
}
