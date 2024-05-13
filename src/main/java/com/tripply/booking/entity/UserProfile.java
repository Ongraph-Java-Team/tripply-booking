package com.tripply.booking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.json.simple.JSONObject;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_profile", schema = "onboarding")
public class UserProfile extends BaseEntity {

    @Column(nullable = false, unique = true)
    private UUID userId;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private JSONObject personalInfo;

    public String getFullName()
    {
        return String.join(" ", firstName, lastName);
    }



}
