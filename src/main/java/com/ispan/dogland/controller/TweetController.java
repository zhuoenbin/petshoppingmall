package com.ispan.dogland.controller;


import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private TweetService tweetService;

    public TweetController(TweetService tweetService){
        this.tweetService = tweetService;
    }

    @GetMapping("/getAllTweet")
    public List<Tweet> allTweet(){
        return tweetService.getAllTweet();
    }

    @PostMapping("/postTweet")
    public
}
