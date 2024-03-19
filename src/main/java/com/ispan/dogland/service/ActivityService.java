package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.ActivityTypeRepository;
import com.ispan.dogland.model.dao.VenueRentalRepository;
import com.ispan.dogland.model.dao.VenueRepository;
import com.ispan.dogland.model.entity.activity.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private VenueRentalRepository venueRentalRepository;
    @Autowired
    private ActivityTypeRepository activityTypeRepository;

    //////////////

    public List<Venue> getAllVenue(){
        return venueRepository.findAll();
    }

}
