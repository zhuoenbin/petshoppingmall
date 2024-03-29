package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.activity.ActivityUserJoined;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityUserJoinedRepository extends JpaRepository<ActivityUserJoined,Integer> {
    ActivityUserJoined findByUserJoinedId (Integer userJoinedId);

    ActivityUserJoined findByVenueActivityAndUser(VenueActivity venueActivity, Users user);

    ActivityUserJoined findByVenueActivityAndUserAndJoinedStatusNot(VenueActivity venueActivity, Users user,String joinedStatus);
}
