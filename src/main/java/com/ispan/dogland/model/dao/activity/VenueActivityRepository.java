package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.VenueActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueActivityRepository extends JpaRepository<VenueActivity,Integer> {
    VenueActivity findByActivityId(Integer activityId);
}
