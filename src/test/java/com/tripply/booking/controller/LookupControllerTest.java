package com.tripply.booking.controller;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.model.ResponseModel;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.request.StateRequest;
import com.tripply.booking.model.request.CountryRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.model.response.CountryResponse;
import com.tripply.booking.model.response.StateResponse;
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
        List<CountryCodeResponse> response = setListOfCountrycode();
        when(lookupService.getAllCountryCode()).thenReturn(response);

        ResponseEntity<ResponseModel<List<CountryCodeResponse>>> result = countryCodeController.getAllCountryCode();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Country Codes retrieved successfully", result.getBody().getMessage());
        assertEquals(HttpStatus.FOUND, result.getBody().getStatus());

        verify(lookupService, times(1)).getAllCountryCode();
    }

    @Test
    void testAddCountry() {
        CountryRequest request = CountryRequest.builder()
                .countryName("India").build();
        CountryResponse response = CountryResponse.builder()
                .countryName("India")
                .id(1L).build();
        when(lookupService.addCountry(request)).thenReturn(response);

        ResponseEntity<ResponseModel<CountryResponse>> result = countryCodeController.addCountry(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Country added successfully", result.getBody().getMessage());
        assertEquals(HttpStatus.CREATED, result.getBody().getStatus());
        assertEquals("India", result.getBody().getData().getCountryName());

        verify(lookupService, times(1)).addCountry(request);

    }

    @Test
    void testAddCountryAlreadyAdded() {
        CountryRequest request = CountryRequest.builder().countryName("India").build();

        when(lookupService.addCountry(request)).thenThrow(new BadRequestException("Country is already Added.it"));

        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> countryCodeController.addCountry(request),
                "Country is already Added."
        );

        assertTrue(thrown.getMessage().contains("Country is already Added."));
        verify(lookupService, times(1)).addCountry(request);
    }

    @Test
    void testGetAllCountry() {
        List<CountryResponse> response = setListOfCountry();
        when(lookupService.getAllCountry()).thenReturn(response);

        ResponseEntity<ResponseModel<List<CountryResponse>>> result = countryCodeController.getAllCountry();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("Country retrieved successfully", result.getBody().getMessage());
        assertEquals(HttpStatus.FOUND, result.getBody().getStatus());

        verify(lookupService, times(1)).getAllCountry();
    }

    @Test
    void testAddState() {
        StateRequest request = StateRequest.builder().stateName("Rajasthan").countryId(1L).build();
        StateResponse response = StateResponse.builder().stateName("Rajasthan").countryId(1L).id(1L).build();
        when(lookupService.addState(request)).thenReturn(response);

        ResponseEntity<ResponseModel<StateResponse>> result = countryCodeController.addState(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("State added successfully", result.getBody().getMessage());
        assertEquals(HttpStatus.CREATED, result.getBody().getStatus());
        assertEquals("Rajasthan", result.getBody().getData().getStateName());
        assertEquals(1L, result.getBody().getData().getCountryId());

        verify(lookupService, times(1)).addState(request);
    }

    @Test
    void testAddStateAlreadyAdded() {
        StateRequest request = StateRequest.builder().stateName("Rajasthan").countryId(1L).build();

        when(lookupService.addState(request)).thenThrow(new BadRequestException("State already Added."));

        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> countryCodeController.addState(request),
                "State already Added."
        );

        assertTrue(thrown.getMessage().contains("State already Added."));
        verify(lookupService, times(1)).addState(request);
    }


    @Test
    void testGetAllState() {
        List<StateResponse> response = setListOfState();
        when(lookupService.getAllStateByCountryId(1L)).thenReturn(response);

        ResponseEntity<ResponseModel<List<StateResponse>>> result = countryCodeController.getAllStateByCountryId(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals("State retrieved successfully", result.getBody().getMessage());
        assertEquals(HttpStatus.FOUND, result.getBody().getStatus());

        verify(lookupService, times(1)).getAllStateByCountryId(1L);
    }

    private List<StateResponse> setListOfState() {
        return Stream.of(
                        new StateResponse(1L, "Rajasthan", 1L),
                        new StateResponse(2L, "Meghalaya", 1L),
                        new StateResponse(3L, "Mizoram", 1L)
                )
                .map(data -> StateResponse.builder()
                        .stateName(data.getStateName())
                        .countryId(data.getCountryId())
                        .id(data.getId())
                        .build())
                .collect(Collectors.toList());
    }

    private List<CountryCodeResponse> setListOfCountrycode() {
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

    private List<CountryResponse> setListOfCountry() {
        return Stream.of(
                        new CountryResponse(1L, "India"),
                        new CountryResponse(2L, "UK"),
                        new CountryResponse(3L, "China")
                )
                .map(data -> CountryResponse.builder()
                        .countryName(data.getCountryName())
                        .id(data.getId())
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
