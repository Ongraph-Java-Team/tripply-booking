package com.tripply.booking.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_manager", schema = "onboarding")
public class HotelManager extends BaseEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "hotel_id"))
    private Hotel hotel;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "user_profile_id"))
    private UserProfile userProfile;

    private Boolean isActive;

}
