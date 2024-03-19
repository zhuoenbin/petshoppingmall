package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.tweet.Tweet;

public interface TweetService {

    public void saveTweetWithUserId(Integer userId, Tweet tweet);
}
