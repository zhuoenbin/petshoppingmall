package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.model.entity.tweet.TweetFollowList;
import com.ispan.dogland.model.entity.tweet.TweetNotification;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TweetService {

    public List<Tweet> findTweetsByUserId(Integer userId);
    public Users findUserByTweetId(Integer tweetId);

    public List<Tweet> findTweetsByUserName(String userName);

    public List<Tweet> getAllTweet();

    public Tweet postNewTweet(Tweet tweet, Integer userId);

    public String saveTweetImgToLocal(MultipartFile file);

    public List<Tweet> getNumOfComment(Integer tweetId);

    public List<Tweet> getAllTweetForPage(int page, int limit);

    public List<Users> findUserLikesByTweetId(Integer tweetId);


    //按讚，建立tweet與user的關聯
    public void createLinkWithTweetAndLike(Integer tweetId, Integer userId);


    public void removeLinkWithTweetAndLike(Integer tweetId, Integer userId);

    public Tweet findTweetByTweetId(Integer tweetId);

    //確認otherUserId是不是已經被myUserId追蹤
    public boolean checkIsFollow(Integer myUserId, Integer otherUserId);

    public boolean followTweetUser(Integer myUserId, Integer otherUserId);

    public void deleteFollowTweetUser(Integer myUserId, Integer otherUserId);

    public List<Tweet> findAllFollowTweetsByUserId(Integer userId);

    public List<Users> findAllFollowUsersByUserId(Integer userId);

    public List<Dog>  findTweetDogsByTweetId(Integer tweetId);

    public Tweet addDogToTweet(Integer dogId, Integer tweetId);

    public Tweet removeDogFromTweet(Integer dogId, Integer tweetId);

    public void sendPostTweetNotificationToFollower(Integer userId,Integer tweetId);

    public void sendReplyNotificationToTweetOwner(Integer hisUserId,Integer hisTweetId,String myName);

    public void sendLikeNotificationToTweetOwner(Integer hisUserId,Integer hisTweetId,String myName);

    public List<TweetNotification> findMyTweetNotifications(Integer userId);

    public List<Tweet> findTweetsAndCommentsByUserId(Integer userId);
    
    public TweetNotification findTweetNotificationByNotifiId(Integer id);

    void saveTweetNotification(TweetNotification t1);
}
