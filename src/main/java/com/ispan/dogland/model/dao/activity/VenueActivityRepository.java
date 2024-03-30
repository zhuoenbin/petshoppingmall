package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.ActivityType;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueActivityRepository extends JpaRepository<VenueActivity,Integer> {
    VenueActivity findByActivityId(Integer activityId);

    Page<VenueActivity> findByActivityType(ActivityType activityType, Pageable pageable);

    List<VenueActivity> findByActivityStatusNot(String activityStatus);



}
