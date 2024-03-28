package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.tweet.TweetFollowListRepository;
import com.ispan.dogland.model.dao.tweet.TweetGalleryRepository;
import com.ispan.dogland.model.dao.tweet.TweetLikeRepository;
import com.ispan.dogland.model.dao.tweet.TweetRepository;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.model.entity.tweet.TweetFollowList;
import com.ispan.dogland.model.entity.tweet.TweetLike;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TweetServiceImpl implements TweetService {

    @Value("${imagepath}")
    private String uploadDir;

    private TweetRepository tweetRepository;
    private UserRepository userRepository;
    private TweetGalleryRepository tweetGalleryRepository;
    private TweetLikeRepository tweetLikeRepository;
    private TweetFollowListRepository tweetFollowListRepository;
    private DogRepository dogRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, UserRepository userRepository, TweetGalleryRepository tweetGalleryRepository, TweetLikeRepository tweetLikeRepository,TweetFollowListRepository tweetFollowListRepository,DogRepository dogRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.tweetGalleryRepository = tweetGalleryRepository;
        this.tweetLikeRepository = tweetLikeRepository;
        this.tweetFollowListRepository = tweetFollowListRepository;
        this.dogRepository = dogRepository;
    }

    @Override
    public List<Tweet> findTweetsByUserId(Integer userId) {
        Users tmp = userRepository.findByUserId(userId);
        if (tmp != null) {
            return tweetRepository.findByUserId(userId);
        }
        return null;
    }

    @Override
    public Users findUserByTweetId(Integer tweetId) {
        return userRepository.findByTweetId(tweetId);
    }

    @Override
    public List<Tweet> findTweetsByUserName(String userName) {
        return tweetRepository.findTweetsByUserName(userName);
    }

    @Override
    public List<Tweet> getAllTweet() {
        List<Tweet> tweets = tweetRepository.findAllTweetsWithGallery();
        List<Tweet> ret = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getPreNode() == 0) {
                ret.add(tweet);
            }
        }
        return ret;
    }

    @Override
    public Tweet postNewTweet(Tweet tweet, Integer userId) {
        Users user = userRepository.findByUserId(userId);
        if (user != null) {
            tweet.setUserName(user.getLastName());
            Tweet t = tweetRepository.save(tweet); //into DB
            t.setUser(user);
            Tweet t1 = tweetRepository.save(t);
            return t1;
        }
        return null;
    }

    @Override
    public String saveTweetImgToLocal(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = timeStamp + "_" + originalFileName;
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }
        Path filePath = Paths.get(uploadDir, fileName);
        try {
            Files.copy(file.getInputStream(), filePath);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Tweet> getNumOfComment(Integer tweetId) {
        return tweetRepository.findCommentByPreNodeId(tweetId);
    }

    @Override
    public List<Tweet> getAllTweetForPage(int page, int limit) {
        Page<Tweet> tweetPage = tweetRepository.findAllTweetsWithGallery(PageRequest.of(page - 1, limit));
        return tweetPage.getContent();
    }

    @Override
    public List<Users> findUserLikesByTweetId(Integer tweetId) {
        return tweetRepository.findUserLikesByTweetId(tweetId);
    }


    @Override
    public void createLinkWithTweetAndLike(Integer tweetId, Integer userId) {
        Users user = userRepository.findByUserId(userId);
        Tweet tweet = tweetRepository.findTweetUserLikesByTweetId(tweetId);
        tweet.addUserLike(user);
        tweetRepository.save(tweet);
    }

    @Override
    public void removeLinkWithTweetAndLike(Integer tweetId, Integer userId) {
        TweetLike ll = tweetLikeRepository.findByTweetIdAndUserId(tweetId, userId);
        if (ll != null) {
            tweetLikeRepository.delete(ll);
        }
    }

    @Override
    public Tweet findTweetByTweetId(Integer tweetId) {
        return tweetRepository.findTweetByTweetId(tweetId);
    }

    @Override
    public boolean checkIsFollow(Integer myUserId, Integer otherUserId) {
        TweetFollowList tmp = tweetFollowListRepository.findByMyUserIdAndOtherUserId(myUserId, otherUserId);
        return tmp != null;
    }
    @Override
    public boolean followTweetUser(Integer myUserId, Integer otherUserId) {
        Users myUser = userRepository.findByUserId(myUserId);
        Users otherUser = userRepository.findByUserId(otherUserId);
        if (myUser != null && otherUser != null) {
            TweetFollowList tmp = new TweetFollowList(myUserId, otherUserId);
            tweetFollowListRepository.save(tmp);
            return true;
        }
        return false;
    }

    @Override
    public void deleteFollowTweetUser(Integer myUserId, Integer otherUserId) {
        tweetFollowListRepository.deleteByUserIdAndFollwerId(myUserId, otherUserId);
    }

    @Override
    public List<Tweet> findAllFollowTweetsByUserId(Integer userId) {
        //該user所有追蹤的人
        List<TweetFollowList> followList = tweetFollowListRepository.findByUserId(userId);
        List<Tweet> totalTweet = new ArrayList<>();
        //把每一個追蹤的人的tweets找出來
        for (TweetFollowList tmp : followList) {
            totalTweet.addAll(tweetRepository.findTweetsWithGalleryWithNoComment(tmp.getFollwerId()));
        }
        return totalTweet;
    }

    @Override
    public List<Users> findAllFollowUsersByUserId(Integer userId) {
        //該user所有追蹤的人
        List<TweetFollowList> followList = tweetFollowListRepository.findByUserId(userId);
        List<Users> totalUsers = new ArrayList<>();
        for (TweetFollowList tmp : followList) {
            totalUsers.add(userRepository.findByUserId(tmp.getFollwerId()));
        }
        return totalUsers;
    }

    @Override
    public List<Dog> findTweetDogsByTweetId(Integer tweetId) {
        Tweet a = tweetRepository.findTweetAndDogsByTweetId(tweetId);
        if(a != null){
            return a.getDogs();
        }
        return null;
    }

    @Override
    public Tweet addDogToTweet(Integer dogId, Integer tweetId) {
        Tweet b = tweetRepository.findTweetAndDogsByTweetIdByLEFTJOIN(tweetId);
        Dog c = dogRepository.findByDogId(dogId);
        b.addDog(c);
        return tweetRepository.save(b);
    }

    @Override
    public Tweet removeDogFromTweet(Integer dogId, Integer tweetId) {
        Tweet b = tweetRepository.findTweetAndDogsByTweetId(tweetId);
        if(b != null){
            List<Dog> dogs = b.getDogs();
            for(Dog d : dogs){
                if(Objects.equals(d.getDogId(), dogId)){
                    dogs.remove(d);
                    tweetRepository.save(b);
                    return b;
                }
            }
        }
        return null;
    }


}
