package com.tripply.booking.entity;

import com.tripply.booking.constants.enums.JobStatus;
import com.tripply.booking.model.request.RoomRequest;
import com.tripply.booking.model.response.RoomUploadResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "room_bulk_job", schema = "onboarding")
public class RoomBulkJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false)
    private UUID hotelId;

    private LocalDateTime updatedAt;

    private Long totalRooms;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_request", columnDefinition = "jsonb")
    private RoomRequest roomRequest;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_upload_responses", columnDefinition = "jsonb")
    private List<RoomUploadResponse> roomUploadResponses;

}
