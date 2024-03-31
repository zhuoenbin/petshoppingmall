package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.ActivityType;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueActivityRepository extends JpaRepository<VenueActivity,Integer> {
    VenueActivity findByActivityId(Integer activityId);
    Page<VenueActivity> findAllByOrderByActivityStartAsc(Pageable pageable);

    Page<VenueActivity> findByActivityType(ActivityType activityType, Pageable pageable);

    //找活動狀態是
    Page<VenueActivity> findByActivityStatusOrderByActivityStartAsc(String activityStatus, Pageable pageable);
    //找活動狀態非
    Page<VenueActivity> findByActivityStatusNotOrderByActivityStartAsc(String activityStatus, Pageable pageable);
    //找類別活動狀態是
    Page<VenueActivity> findByActivityTypeAndActivityStatusOrderByActivityStartAsc(ActivityType activityType,String activityStatus, Pageable pageable);
    //找類別活動狀態非
    Page<VenueActivity> findByActivityTypeAndActivityStatusNotOrderByActivityStartAsc(ActivityType activityType,String activityStatus, Pageable pageable);



}
