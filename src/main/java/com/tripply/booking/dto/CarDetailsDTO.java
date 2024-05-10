package com.tripply.booking.dto;

import jakarta.persistence.*;

import javax.xml.stream.Location;
import java.sql.Timestamp;

@Entity
@Table(name = "car_details")
public class CarDetailsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String carId;
    private String make;

    @Column(name = "car_model")
    private String model;

    @Column(name = "manufacture_year")
    private String year;

    @Column(name = "rental_company")
    private String rentalCompany;

    @Column(name = "pickup_location")
    private Location location;

    @Column(name = "car_rental_rate")
    private int rate;

    @Column(name = "car_availability")
    private boolean availability;

    @Column(name = "created_on")
    private Timestamp createdAt;

    @Column(name = "last_updated_on")
    private Timestamp updatedAt;
}
