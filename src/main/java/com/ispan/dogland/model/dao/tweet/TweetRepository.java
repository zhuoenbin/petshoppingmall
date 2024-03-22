package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

    @Query("SELECT t FROM Tweet t JOIN FETCH t.tweetGalleries WHERE t.user.userId = ?1")
    List<Tweet> findByUserId(Integer userId);


    //使用tweetId找找推特與圖
    @Query("SELECT t FROM Tweet t JOIN FETCH t.tweetGalleries WHERE t.tweetId = ?1")
    Tweet findByTweetId(Integer tweetId);

//    @Query("SELECT t FROM Tweet t JOIN FETCH t.tweetGalleries")
    @Query("SELECT t FROM Tweet t LEFT JOIN FETCH t.tweetGalleries")
    List<Tweet> findAllTweetsWithGallery();

}
