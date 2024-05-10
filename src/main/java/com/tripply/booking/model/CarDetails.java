package com.tripply.booking.model;

import lombok.*;

import javax.xml.stream.Location;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDetails {
   private String carId;
   private String make;
   private String model;
   private String year;

   private String rentalCompany;
   private Location location;
   private int rate;
   private boolean availability;
   private Timestamp createdAt;
   private Timestamp updatedAt;

}
