package com.tripply.booking.model;

import java.util.UUID;

public interface TopRatedHotelProjection {

    UUID getHotelId();
    String getHotelName();
    Double getAverageRating();

}
