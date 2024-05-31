package com.tripply.booking.entity;

import com.tripply.booking.model.request.AmentiesRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel", schema = "onboarding")
public class Hotel extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "registration_number")
    private String registrationNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "state_id")
    private String stateId;
    @Column(name = "country_id")
    private String countryId;
    @Column(name = "description")
    private String description;
    @Column(name = "website")
    private String website;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "amenities", columnDefinition = "jsonb")
    private List<AmentiesRequest> amenities;

}
