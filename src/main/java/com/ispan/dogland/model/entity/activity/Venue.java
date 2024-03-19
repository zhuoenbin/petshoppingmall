package com.ispan.dogland.model.entity.activity;

import jakarta.persistence.*;

@Table
@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer venueId;
    private String venueName;
    private Integer venueCapacityDog;
    private Integer venueCapacityUser;
    private String venueDescription;
    private Integer venueRent;

    //////////////////////////////////////
    public Venue() {
    }

    //////////////////////////////////////
    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Integer getVenueCapacityDog() {
        return venueCapacityDog;
    }

    public void setVenueCapacityDog(Integer venueCapacityDog) {
        this.venueCapacityDog = venueCapacityDog;
    }

    public Integer getVenueCapacityUser() {
        return venueCapacityUser;
    }

    public void setVenueCapacityUser(Integer venueCapacityUser) {
        this.venueCapacityUser = venueCapacityUser;
    }

    public String getVenueDescription() {
        return venueDescription;
    }

    public void setVenueDescription(String venueDescription) {
        this.venueDescription = venueDescription;
    }

    public Integer getVenueRent() {
        return venueRent;
    }

    public void setVenueRent(Integer venueRent) {
        this.venueRent = venueRent;
    }
}
