package com.ispan.dogland.controller;

import com.ispan.dogland.model.entity.activity.Venue;
import com.ispan.dogland.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/venue")
    public List<Venue> allVenue(){
        return activityService.getAllVenue();
    }
}
