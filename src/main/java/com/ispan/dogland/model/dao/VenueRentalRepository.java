package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.activity.VenueRental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRentalRepository extends JpaRepository<VenueRental,Integer> {

    VenueRental findByVenueRentalId(Integer venueRentalId);
}
