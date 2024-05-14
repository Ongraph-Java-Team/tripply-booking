package com.tripply.booking.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AmentiesRequest {

    @NotBlank(message = "amenityName can't be empty")
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;

}
