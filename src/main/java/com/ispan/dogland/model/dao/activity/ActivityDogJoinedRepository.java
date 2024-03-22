package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.ActivityDogJoined;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityDogJoinedRepository extends JpaRepository<ActivityDogJoined,Integer> {
    ActivityDogJoined findByDogJoinedId(Integer dogJoinedId);
}
