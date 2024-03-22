package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.tweet.Tweet;

import java.util.List;

public interface TweetService {
    public List<Tweet> findTweetsByUserId(Integer userId);

    public List<Tweet> getAllTweet();

    public boolean postTweet(Tweet tweet, Integer userId);
}
