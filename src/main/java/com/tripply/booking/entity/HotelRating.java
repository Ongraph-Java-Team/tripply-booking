package com.tripply.booking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_rating", schema = "onboarding")
public class HotelRating extends BaseEntity {

    @Column(name = "rating")
    private Float rating;
    @Column(name = "user_id")
    private UUID userId;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "hotel_id"))
    private Hotel hotel;
    @Column(name = "comment")
    private String comment;

}
