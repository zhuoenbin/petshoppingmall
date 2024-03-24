package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.ActivityUserJoined;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityUserJoinedRepository extends JpaRepository<ActivityUserJoined,Integer> {
    ActivityUserJoined findByUserJoinedId (Integer userJoinedId);
}
