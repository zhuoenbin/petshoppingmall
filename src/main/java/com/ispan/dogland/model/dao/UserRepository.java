package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUserId(Integer userId);

    Users findByUserEmail(String userEmail);

    @Query("SELECT u FROM Users u LEFT JOIN u.tweets t WHERE t.tweetId = ?1")
    Users findByTweetId(Integer tweetId);


}
