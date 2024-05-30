package com.tripply.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "amenity", schema="onboarding")
public class Amenity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "amenity_name", nullable = false, length = 255, unique = true)
	private String amenityName;

	@Column(name = "description", length = 1000)
	private String description;

	@Column(name = "icon_url", length = 255)
	private String iconUrl;
}