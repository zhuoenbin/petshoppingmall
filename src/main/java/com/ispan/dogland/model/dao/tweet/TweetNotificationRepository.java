package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.tweet.TweetNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetNotificationRepository extends JpaRepository<TweetNotification, Integer> {
}
