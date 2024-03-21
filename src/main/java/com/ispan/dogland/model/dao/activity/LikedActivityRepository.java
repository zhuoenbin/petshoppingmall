package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.LikedActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedActivityRepository extends JpaRepository<LikedActivity,Integer> {
    LikedActivity findByLikedActivityId(Integer likedActivityId);
}
