package com.tripply.booking.service;

import com.tripply.booking.Exception.BadRequestException;
import com.tripply.booking.entity.Country;
import com.tripply.booking.entity.CountryCode;
import com.tripply.booking.model.request.CountryCodeRequest;
import com.tripply.booking.model.request.CountryRequest;
import com.tripply.booking.model.response.CountryCodeResponse;
import com.tripply.booking.model.response.CountryResponse;
import com.tripply.booking.repository.CountryCodeRepository;
import com.tripply.booking.repository.CountryRepository;
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
    private CountryCodeRepository countryCodeRepository;

    @Mock
    private CountryRepository countryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCountryCode() {
        CountryCodeRequest request = setRequest();
        CountryCode countryCode = setCountryCode(request);

        when(countryCodeRepository.getCountryCodeByCode(request.getCode())).thenReturn(Optional.empty());
        when(countryCodeRepository.save(any(CountryCode.class))).thenReturn(countryCode);

       CountryCodeResponse response = lookupService.addCountryCode(request);

        assertNotNull(response);
        assertEquals("AF", response.getCode());

        verify(countryCodeRepository, times(1)).save(any(CountryCode.class));
        verify(countryCodeRepository, times(1)).getCountryCodeByCode(request.getCode());
    }

    @Test
    void testAddCountryCodeAlreadyAdded() {
        CountryCodeRequest request = setRequest();
        when(countryCodeRepository.getCountryCodeByCode("AF")).thenReturn(Optional.of(setCountryCode(request)));

        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> lookupService.addCountryCode(request),
                "Country Code already Added."
        );

        assertTrue(thrown.getMessage().contains("Country Code already Added."));
        verify(countryCodeRepository, times(1)).getCountryCodeByCode("AF");
        verify(countryCodeRepository, never()).save(any(CountryCode.class));
    }

    @Test
    void testGetAllCountryCode() {
        List<CountryCode> countryCodes = setListResponse();

        when(countryCodeRepository.findAll()).thenReturn(countryCodes);

        List<CountryCodeResponse> response = lookupService.getAllCountryCode();

        assertNotNull(response);
        verify(countryCodeRepository, times(1)).findAll();
    }

    @Test
    void testAddCountry() {
        CountryRequest request = CountryRequest.builder().countryName("India").build();
        Country country = Country.builder().countryName(request.getCountryName()).build();

        when(countryRepository.getByCountryName(request.getCountryName())).thenReturn(Optional.empty());
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        CountryResponse response = lookupService.addCountry(request);

        assertNotNull(response);
        assertEquals("India", response.getCountryName());

        verify(countryRepository, times(1)).save(any(Country.class));
        verify(countryRepository, times(1)).getByCountryName(request.getCountryName());
    }

    @Test
    void testAddCountryAlreadyAdded() {
        CountryRequest request = CountryRequest.builder().countryName("India").build();
        Country country = Country.builder().countryName(request.getCountryName()).build();
        when(countryRepository.getByCountryName("India")).thenReturn(Optional.of(country));

        BadRequestException thrown = assertThrows(
                BadRequestException.class,
                () -> lookupService.addCountry(request),
                "Country is already Added."
        );

        assertTrue(thrown.getMessage().contains("Country is already Added."));
        verify(countryRepository, times(1)).getByCountryName("India");
        verify(countryRepository, never()).save(any(Country.class));
    }

    @Test
    void testGetAllCountry() {
        List<Country> countryList = setListOfCountry();

        when(countryRepository.findAll()).thenReturn(countryList);

        List<CountryResponse> response = lookupService.getAllCountry();

        assertNotNull(response);
        verify(countryRepository, times(1)).findAll();
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

    private List<Country> setListOfCountry() {
        return Stream.of(
                        new Country(1L, "India"),
                        new Country(2L, "UK"),
                        new Country(3L, "China")
                )
                .map(data -> Country.builder()
                        .countryName(data.getCountryName())
                        .id(data.getId())
                        .build())
                .collect(Collectors.toList());
    }
}
