package com.tripply.booking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomCarDetailsResponse {
    private int totalItems;
    private int pageNo;
    private int pageSize;
    private List<CarDetailsResponse> carDetails;
}
