package com.ispan.dogland.model.entity.activity;

import jakarta.persistence.*;

import java.util.List;

@Table
@Entity
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityTypeId;
    private String activityTypeName;

    @OneToMany(mappedBy = "activityType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VenueActivity> venueActivityList;

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public List<VenueActivity> getVenueActivityList() {
        return venueActivityList;
    }

    public void setVenueActivityList(List<VenueActivity> venueActivityList) {
        this.venueActivityList = venueActivityList;
    }
}
