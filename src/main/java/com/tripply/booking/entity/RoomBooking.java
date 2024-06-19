package com.tripply.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_booking", schema = "onboarding")
public class RoomBooking extends BaseEntity{

    private UUID userId;

    private UUID hotelId;

    private String roomType;

    private String roomCategory;

    private Double totalCharge;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "room_ids", columnDefinition = "jsonb")
    private List<Long> roomIds;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

}
