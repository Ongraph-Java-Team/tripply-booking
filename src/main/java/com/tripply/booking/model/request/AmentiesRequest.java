package com.tripply.booking.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmentiesRequest {

	@NotEmpty(message = "Amenity name is required")
    @Size(max = 255, message = "Amenity name must be less than 255 characters")
    private String amenityName;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @Size(max = 255, message = "Icon URL must be less than 255 characters")
    private String iconURL;

}
