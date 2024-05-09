package com.tripply.booking.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "hotel")
public class HotelRequestBean {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "name")
	    private String name;

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

	    @Column(name = "amenities")
	    private List<String> amenities;

//	    @Column(name = "manager")
//	    private ManagerDetails managerDetails;

	    // Constructors, getters, and setters
	}

