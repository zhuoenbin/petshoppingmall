package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.tweet.TweetGalleryRepository;
import com.ispan.dogland.model.dao.tweet.TweetRepository;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
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

@Service
public class TweetServiceImpl implements TweetService {

    @Value("${imagepath}")
    private String uploadDir;

    private TweetRepository tweetRepository;
    private UserRepository userRepository;
    private TweetGalleryRepository tweetGalleryRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, UserRepository userRepository,TweetGalleryRepository tweetGalleryRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.tweetGalleryRepository = tweetGalleryRepository;
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
    public List<Tweet> getAllTweet() {
        List<Tweet> tweets = tweetRepository.findAllTweetsWithGallery();
        List<Tweet> ret = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if(tweet.getPreNode()==0){
                ret.add(tweet);
            }
        }
        return ret;
    }

    @Override
    public boolean postNewTweet(Tweet tweet, Integer userId) {
        Users user = userRepository.findByUserId(userId);
        if (user != null) {
            tweet.setUserName(user.getLastName());
            Tweet t = tweetRepository.save(tweet); //into DB
            t.setUser(user);
            tweetRepository.save(t);
            return true;
        }
        return false;
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
}
