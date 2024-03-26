package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TweetService {

    public List<Tweet> findTweetsByUserId(Integer userId);
    public Users findUserByTweetId(Integer tweetId);

    public List<Tweet> getAllTweet();

    public boolean postNewTweet(Tweet tweet, Integer userId);

    public String saveTweetImgToLocal(MultipartFile file);

    public List<Tweet> getNumOfComment(Integer tweetId);

    public List<Tweet> getAllTweetForPage(int page, int limit);

    public List<Users> findUserLikesByTweetId(Integer tweetId);


    //按讚，建立tweet與user的關聯
    public void createLinkWithTweetAndLike(Integer tweetId, Integer userId);


    public void removeLinkWithTweetAndLike(Integer tweetId, Integer userId);
}
