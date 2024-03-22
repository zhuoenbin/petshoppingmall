package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.tweet.Tweet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TweetService {

    public List<Tweet> findTweetsByUserId(Integer userId);

    public List<Tweet> getAllTweet();

    public boolean postNewTweet(Tweet tweet, Integer userId);

    public String saveTweetImgToLocal(MultipartFile file);
}
