package com.tripply.booking.dto;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "car_details")
public class CarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

//    @Size(max = 12, message = "Registration number must be at most 12 characters.")
    @Column(length = 12, unique = true)
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
}
