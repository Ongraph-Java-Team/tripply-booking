package com.tripply.booking.model.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryCodeRequest {

    @NotNull(message = "Name should not be null")
    private String name;

    @NotNull(message = "Dial Code should not be null")
    private String dialCode;

    @NotNull(message = "Code should not be null")
    @Column(unique = true)
    private String code;

}
