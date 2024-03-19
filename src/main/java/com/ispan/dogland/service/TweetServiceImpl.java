package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.tweet.TweetRepository;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetServiceImpl implements TweetService {
    private TweetRepository tweetRepository;
    private UserRepository userRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveTweetWithUserId(Integer userId, Tweet tweet) {
        Tweet tmp = tweetRepository.save(tweet);
        tmp.setUser(userRepository.findByUserId(userId));
        tweetRepository.save(tmp);
    }

    
}
