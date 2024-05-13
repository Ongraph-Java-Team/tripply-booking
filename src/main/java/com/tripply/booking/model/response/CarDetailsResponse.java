package com.tripply.booking.model.response;

import com.tripply.booking.entity.CarDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDetailsResponse {
    private List<CarDetails> carDetails;
}
