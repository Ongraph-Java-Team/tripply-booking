package com.tripply.booking.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingRequest {
    @NotNull(message = "userId can't be null")
    private UUID userId;
    @NotNull(message = "hotelId can't be null")
    private UUID hotelId;
    @NotNull(message = "rating can't be null")
    private Float rating;
    private String comment;
}
