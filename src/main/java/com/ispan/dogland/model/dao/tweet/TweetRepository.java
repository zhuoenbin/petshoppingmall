package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

    @Query("SELECT t FROM Tweet t LEFT JOIN t.tweetGalleries WHERE t.user.userId = ?1")
    List<Tweet> findByUserId(Integer userId);


    //使用tweetId找找推特與圖
    @Query("SELECT t FROM Tweet t LEFT JOIN t.tweetGalleries WHERE t.tweetId = ?1")
    Tweet findByTweetId(Integer tweetId);

    //找所有推文與圖片
    @Query("SELECT t FROM Tweet t LEFT JOIN FETCH t.tweetGalleries")
    List<Tweet> findAllTweetsWithGallery();


    @Query("SELECT t FROM Tweet t LEFT JOIN t.tweetGalleries WHERE t.preNode = ?1")
    List<Tweet> findCommentByPreNodeId(Integer preNodeId);

    @Query("SELECT t FROM Tweet t LEFT JOIN FETCH t.tweetGalleries")
    Page<Tweet> findAllTweetsWithGallery(Pageable pageable);
}
