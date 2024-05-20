package com.tripply.booking.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingResponse {

    private UUID id;
    private UUID userId;
    private UUID hotelId;
    private Integer rating;
    private String comments;

}
