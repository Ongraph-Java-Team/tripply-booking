package com.tripply.booking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "car_details")
public class CarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @Column(length = 12, nullable=false, unique = true)
    private String registrationNo;

    @Column(name = "car_model")
    private String model;

    @Column(name = "manufacture_year")
    private String year;

    @Column(name = "rental_company")
    private String rentalCompany;

    @Column(name = "pickup_location")
    private String location;

    @Column(name = "car_rental_rate")
    private int rate;

    @Column(name = "car_availability")
    private boolean availability;

    @Column(name = "created_on")
    private Timestamp createdAt;

    @Column(name = "last_updated_on")
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis()); // Set current timestamp when entity is created
        this.updatedAt = new Timestamp(System.currentTimeMillis()); // Set updatedAt initially same as createdAt
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis()); // Set updatedAt timestamp when entity is updated
    }
}
