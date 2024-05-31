package com.tripply.booking.entity;

import com.tripply.booking.model.request.AmentiesRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "room", schema = "onboarding")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer roomNumber;
	private Integer floor;
	private String category;
	private String type;
	@Column(name = "how_to_reach", length = 500)
	private String howToReach;
	private Double price;
	private String description;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "room_amenities",
			schema = "onboarding",
			joinColumns = @JoinColumn(name = "room_id"),
			inverseJoinColumns = @JoinColumn(name = "amenity_id")
	)
	private Set<Amenity> amenities;
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "extra_amenities", columnDefinition = "jsonb")
	private List<AmentiesRequest> extraAmenties;
	@ManyToOne
	@JoinColumn(name = "hotels_id")
	private Hotel hotel;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@Column(nullable = false)
	private String createdBy;
	private LocalDateTime updatedAt;

}
