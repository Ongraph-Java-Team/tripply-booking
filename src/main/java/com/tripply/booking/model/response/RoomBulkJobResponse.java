package com.tripply.booking.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tripply.booking.constants.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomBulkJobResponse {

    private Long jobId;
    private JobStatus jobStatus;
    private LocalDateTime createdOn;
    private Long totalRooms;

}
