package com.tripply.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateResponse {
    private Long id;
    private String stateName;
    private Long countryId;
}

