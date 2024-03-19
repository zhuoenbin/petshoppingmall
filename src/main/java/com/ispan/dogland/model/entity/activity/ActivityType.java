package com.ispan.dogland.model.entity.activity;

import jakarta.persistence.*;

@Table
@Entity
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityTypeId;

    private String activityTypeName;
}
