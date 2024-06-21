package com.tripply.booking.entity;

import com.tripply.booking.constants.enums.BookingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "room_booking_stage", schema = "onboarding")
public class RoomBookingStage extends BaseEntity{
    UUID bookingId;
    @Enumerated(EnumType.STRING)
    BookingStatus bookingStatus;
    boolean isActive;
}
