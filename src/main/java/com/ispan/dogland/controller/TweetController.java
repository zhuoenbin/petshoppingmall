package com.ispan.dogland.controller;


import com.ispan.dogland.model.dto.TweetDto;
import com.ispan.dogland.model.dto.TweetLikesCheckResponse;
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
import java.util.Map;

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


//    @GetMapping("/getAllTweet")
//    public List<Tweet> allTweet(){
//        return tweetService.getAllTweet();
//    }

    @GetMapping("/getAllTweet")
    public List<Tweet> allTweet(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int limit){
        return tweetService.getAllTweetForPage(page, limit);
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


    //取得回文的數量
    @GetMapping("/getNumOfComment/{tweetId}")
    public Integer getNumOfComment(@PathVariable Integer tweetId) {
        return tweetService.getNumOfComment(tweetId).size(); // Assuming tweetService has a method to get the number of comments for a tweet
    }

    ////取得回文的內容
    @GetMapping("/getComments/{tweetId}")
    public List<Tweet> getComments(@PathVariable Integer tweetId) {
        return tweetService.getNumOfComment(tweetId); // Assuming tweetService has a method to get the number of comments for a tweet
    }

    //取得userId發的所有tweets
    @GetMapping("/getTweetsByUserId/{userId}")
    public List<Tweet> getTweetsByUserId(@PathVariable Integer userId) {
        return tweetService.findTweetsByUserId(userId);
    }

    @GetMapping("/getUserTweetsByTweetId/{tweetId}")
    public List<Tweet> getUserTweetsByTweetId(@PathVariable Integer tweetId) {
        Users user = tweetService.findUserByTweetId(tweetId);
        return tweetService.findTweetsByUserId(user.getUserId());
    }


    //找到該則tweet的所有按讚數量
    @GetMapping("/getTweetLikesNum")
    public ResponseEntity<TweetLikesCheckResponse> getTweetLikesNum(@RequestParam Integer tweetId , @RequestParam Integer userId) {
        List<Users> users = tweetService.findUserLikesByTweetId(tweetId);
        Users user = accountService.getUserDetailById(userId);
        TweetLikesCheckResponse tweetLikesCheckResponse = new TweetLikesCheckResponse();

        tweetLikesCheckResponse.setTweetLikesNum(tweetService.findUserLikesByTweetId(tweetId).size());

        if (users.contains(user)) {
            // user 在 users 列表中
            tweetLikesCheckResponse.setIsUserLiked(1);
        } else {
            // user 不在 users 列表中
            tweetLikesCheckResponse.setIsUserLiked(0);
        }


        return ResponseEntity.ok(tweetLikesCheckResponse);
    }

    //取得該貼文所有按讚的用戶
    @GetMapping("/getTweetLikesUser")
    public ResponseEntity<List<Users>> getTweetLikesUser(@RequestParam Integer tweetId) {
        List<Users> users = tweetService.findUserLikesByTweetId(tweetId);
        return ResponseEntity.ok(users);
    }


    //使用者按讚，使用者與like建立連結
    @PostMapping("/getLikeLink")
    public String likeTweet(@RequestParam Integer userId,@RequestParam Integer tweetId) {
        System.out.println("userId: " + userId + " tweetId: " + tweetId);

        tweetService.createLinkWithTweetAndLike(tweetId, userId);

        return "Liked successfully!";
    }


    //取消讚
    @PostMapping("/removeLikeLink")
    public String removeLikeTweet(@RequestParam Integer userId,@RequestParam Integer tweetId) {
        tweetService.removeLinkWithTweetAndLike(tweetId, userId);
        return "removeLikeTweet successfully!";
    }

    //回覆貼文(純文字)
    @PostMapping("/replyTweet")
    public String replyTweet(@RequestParam Integer tweetId, @RequestParam Integer memberId, @RequestParam String tweetContent) {
            System.out.println("tweetId: " + tweetId + " memberId: " + memberId + " tweetContent: " + tweetContent);
        Tweet tweet = new Tweet();
        tweet.setPreNode(tweetId);
        tweet.setPostDate(new Date());
        tweet.setTweetStatus(1);
        tweet.setNumReport(0);
        tweet.setTweetContent(tweetContent);
        tweetService.postNewTweet(tweet, memberId);
        return "replyTweet successfully!";
    }

}
