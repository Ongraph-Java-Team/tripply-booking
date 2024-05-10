package com.tripply.booking.model.response;

import com.tripply.booking.model.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private List<ErrorDetails> errors;

}
