package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

    List<Tweet> findByUser(Users user);

    @Query("SELECT t FROM Tweet t WHERE t.user.userId = ?1")
    List<Tweet> findByUserId(Integer userId);


}
