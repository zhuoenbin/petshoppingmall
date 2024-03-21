package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.tweet.TweetRepository;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Tweet> findTweetsByUserId(Integer userId) {
        Users tmp = userRepository.findByUserId(userId);
        if (tmp != null) {
            return tweetRepository.findByUserId(userId);
        }
        return null;
    }
}
