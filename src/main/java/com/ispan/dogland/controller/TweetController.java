package com.ispan.dogland.controller;


import com.ispan.dogland.model.dto.TweetDto;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.model.entity.tweet.TweetGallery;
import com.ispan.dogland.service.interfaceFile.AccountService;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetController {



    private TweetService tweetService;

    private AccountService accountService;

    @Autowired
    public TweetController(TweetService tweetService, AccountService accountService) {
        this.tweetService = tweetService;
        this.accountService = accountService;
    }

    public TweetController(TweetService tweetService){
        this.tweetService = tweetService;
    }

    @GetMapping("/getAllTweet")
    public List<Tweet> allTweet(){
        return tweetService.getAllTweet();
    }

    @PostMapping("/postTweetWithPhoto")
    public ResponseEntity<String> postTweet(@RequestParam Integer memberId,
                                            @RequestParam String tweetContent,
                                            @RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Image file is empty");
        }
        //把圖片存到本地
        String imgFileName = tweetService.saveTweetImgToLocal(file);

        TweetGallery tweetGallery = new TweetGallery();
        tweetGallery.setImgPath(imgFileName);
        if (memberId != null) {
            Users user = accountService.getUserDetailById(memberId);
            Tweet tweet = new Tweet();
            tweet.setUserName(user.getLastName());
            tweet.setPreNode(0);
            tweet.setPostDate(new Date());
            tweet.setTweetStatus(1);
            tweet.setNumReport(0);
            if(tweetContent != null){
                tweet.setTweetContent(tweetContent);
            }
            tweet.addTweetGallery(tweetGallery);
            tweetService.postNewTweet(tweet, memberId);
        }
        return ResponseEntity.ok("Tweet posted successfully");
    }

    @PostMapping("/postTweetOnlyText")
    public ResponseEntity<String> postTweetOnlyText(@RequestParam Integer memberId, @RequestParam String tweetContent) {

        if (memberId != null) {
            Users user = accountService.getUserDetailById(memberId);
            Tweet tweet = new Tweet();
            tweet.setUserName(user.getLastName());
            tweet.setPreNode(0);
            tweet.setPostDate(new Date());
            tweet.setTweetStatus(1);
            tweet.setNumReport(0);
            if(tweetContent != null){
                tweet.setTweetContent(tweetContent);
            }
            tweetService.postNewTweet(tweet, memberId);
        }
        return ResponseEntity.ok("Tweet posted successfully");
    }


    @GetMapping("/getImage/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        String imagePath = "D:/image/" + fileName;

        try {
            Path filePath = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

}
