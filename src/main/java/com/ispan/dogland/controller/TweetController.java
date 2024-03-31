package com.ispan.dogland.controller;


import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.tweet.TweetRepository;
import com.ispan.dogland.model.dto.TweetDto;
import com.ispan.dogland.model.dto.TweetLikesCheckResponse;
import com.ispan.dogland.model.dto.UserDto;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.model.entity.tweet.TweetGallery;
import com.ispan.dogland.model.entity.tweet.TweetNotification;
import com.ispan.dogland.model.entity.tweet.TweetReport;
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
import java.util.*;

@RestController
@RequestMapping("/tweet")
public class TweetController {

    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private TweetRepository tweetRepository;

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
    public List<Tweet> allTweet(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int limit) {
        return tweetService.getAllTweetForPage(page, limit);
    }

    @GetMapping("/getTweetById/{tweetId}")
    public Tweet getTweetById(@PathVariable Integer tweetId) {
        return tweetService.findTweetByTweetId(tweetId);
    }

    @GetMapping("/getUserByTweetId/{tweetId}")
    public Users getUserByTweetId(@PathVariable Integer tweetId) {
        return tweetService.findUserByTweetId(tweetId);
    }

    @PostMapping("/postTweetWithPhoto")
    public ResponseEntity<String> postTweet(@RequestParam Integer memberId,
                                            @RequestParam String tweetContent,
                                            @RequestParam("image") MultipartFile file,
                                            @RequestParam List<Integer> dogList) {
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
            if (tweetContent != null) {
                tweet.setTweetContent(tweetContent);
            }
            tweet.addTweetGallery(tweetGallery);
            Tweet tweet1 = tweetService.postNewTweet(tweet, memberId);

            //狗的tags
            if(!dogList.isEmpty()){
                Integer tweetId =  tweet1.getTweetId();
                for (Integer dogId : dogList) {
                    Tweet b = tweetRepository.findTweetAndDogsByTweetIdByLEFTJOIN(tweetId);
                    Dog c = dogRepository.findByDogId(dogId);
                    b.addDog(c);
                    Tweet b2 = tweetRepository.save(b);
                }
            }

            //發送通知
            tweetService.sendPostTweetNotificationToFollower(memberId, tweet1.getTweetId());
        }
        return ResponseEntity.ok("Tweet posted successfully");
    }

    @PostMapping("/postTweetOnlyText")
    public ResponseEntity<String> postTweetOnlyText(@RequestParam Integer memberId, @RequestParam String tweetContent,@RequestParam List<Integer> dogList) {
        if (memberId != null) {
            Users user = accountService.getUserDetailById(memberId);
            Tweet tweet = new Tweet();
            tweet.setUserName(user.getLastName());
            tweet.setPreNode(0);
            tweet.setPostDate(new Date());
            tweet.setTweetStatus(1);
            tweet.setNumReport(0);
            if (tweetContent != null) {
                tweet.setTweetContent(tweetContent);
            }
            Tweet tweet1 = tweetService.postNewTweet(tweet, memberId);

            //狗的tags
            if(!dogList.isEmpty()){
                Integer tweetId =  tweet1.getTweetId();
                for (Integer dogId : dogList) {
			        Tweet b = tweetRepository.findTweetAndDogsByTweetIdByLEFTJOIN(tweetId);
			        Dog c = dogRepository.findByDogId(dogId);
			        b.addDog(c);
			        Tweet b2 = tweetRepository.save(b);
                }
            }

            //發送通知
            tweetService.sendPostTweetNotificationToFollower(memberId, tweet1.getTweetId());


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

    //取得userId發的所有tweets，目前用在我的推文頁面
    @GetMapping("/getTweetsByUserId/{userId}")
    public List<Tweet> getTweetsByUserId(@PathVariable Integer userId) {
        return tweetService.findTweetsAndCommentsByUserId(userId);
    }

    @GetMapping("/getTweetsByUserIdWithNoComments/{userId}")
    public List<Tweet> getTweetsByUserIdWithNoComments(@PathVariable Integer userId) {
        return tweetService.findTweetsByUserId(userId);
    }

    @GetMapping("/getTweetsByUserName/{userName}")
    public List<Tweet> getTweetsByUserName(@PathVariable String userName) {
        return tweetService.findTweetsByUserName(userName);
    }

    @GetMapping("/getUserTweetsByTweetId/{tweetId}")
    public List<Tweet> getUserTweetsByTweetId(@PathVariable Integer tweetId) {
        Users user = tweetService.findUserByTweetId(tweetId);
        return tweetService.findTweetsByUserId(user.getUserId());
    }


    //找到該則tweet的所有按讚數量
    @GetMapping("/getTweetLikesNum")
    public ResponseEntity<TweetLikesCheckResponse> getTweetLikesNum(@RequestParam Integer tweetId, @RequestParam Integer userId) {
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
    public String likeTweet(@RequestParam Integer userId, @RequestParam Integer tweetId) {
        tweetService.createLinkWithTweetAndLike(tweetId, userId);
        return "Liked successfully!";
    }


    //取消讚
    @PostMapping("/removeLikeLink")
    public String removeLikeTweet(@RequestParam Integer userId, @RequestParam Integer tweetId) {
        tweetService.removeLinkWithTweetAndLike(tweetId, userId);
        return "removeLikeTweet successfully!";
    }

    //回覆貼文(純文字)
    @PostMapping("/replyTweet")
    public String replyTweet(@RequestParam Integer tweetId, @RequestParam Integer memberId, @RequestParam String tweetContent) {
        Tweet tweet = new Tweet();
        tweet.setPreNode(tweetId);
        tweet.setPostDate(new Date());
        tweet.setTweetStatus(1);
        tweet.setNumReport(0);
        tweet.setTweetContent(tweetContent);
        tweetService.postNewTweet(tweet, memberId);
        return "replyTweet successfully!";
    }

    //確認是否有追蹤關係
    @PostMapping("/checkFollerRelationship")
    public Map<String, Object> checkFollowerRelationship(@RequestBody Map<String, String> request) {
        String myId = (String) request.get("myId");
        String tweetId = (String) request.get("tweetUserId");

        Integer myIntegerId = Integer.parseInt(myId);
        Integer tweetUserIntegerId = Integer.parseInt(tweetId);

        Users user = accountService.findUsersByTweetId(tweetUserIntegerId);
        boolean isFollowing = tweetService.checkIsFollow(myIntegerId, user.getUserId());
        Map<String, Object> response = new HashMap<>();
        response.put("isFollowing", isFollowing);
        response.put("userId", user.getUserId());
        return response;
    }

    // 建立追蹤關係
    @PostMapping("/getFollerRelationship")
    public String getFollowerRelationship(@RequestBody Map<String, String> request) {
        String myId = request.get("myId");
        String otherId = request.get("otherId");
        boolean p = tweetService.followTweetUser(Integer.parseInt(myId), Integer.parseInt(otherId));
        return String.valueOf(p);
    }
    //取消追蹤關係
    @PostMapping("/removeFollerRelationship")
    public String removeFollowerRelationship(@RequestBody Map<String, String> request) {
        String myId = request.get("myId");
        String otherId = request.get("otherId");
        tweetService.deleteFollowTweetUser(Integer.parseInt(myId), Integer.parseInt(otherId));
        boolean p = tweetService.checkIsFollow(Integer.parseInt(myId), Integer.parseInt(otherId));
        return String.valueOf(!p);
    }


    @GetMapping("/getMyFollowTweets")
    public List<Tweet> getMyFollowweet(@RequestParam Integer userId) {

        List<Tweet> tweets = tweetService.findAllFollowTweetsByUserId(userId);
        Collections.sort(tweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet tweet1, Tweet tweet2) {
                return tweet2.getPostDate().compareTo(tweet1.getPostDate()); // 按照 postDate 降序排序
            }
        });
        return tweets;
    }

    @GetMapping("/getMyFollowUsers")
    public List<Users> getMyFollowUsers(@RequestParam Integer userId) {
        return tweetService.findAllFollowUsersByUserId(userId);
    }


    @GetMapping("/getTweetDogTags/{tweetId}")
    public List<Dog> getTweetDogTags(@PathVariable Integer tweetId) {
        return tweetService.findTweetDogsByTweetId(tweetId);

    }

    @GetMapping("/getMyNotify/{userId}")
    public List<TweetNotification> getMyNotify(@PathVariable Integer userId) {
        return tweetService.findMyTweetNotifications(userId);
    }

    @GetMapping("/sendReplyNotify")
    public String sendReplyNotify(
            @RequestParam("hisUserId") Integer hisUserId,
            @RequestParam("hisTweetId") Integer hisTweetId,
            @RequestParam("myName") String myName
    ) {
        tweetService.sendReplyNotificationToTweetOwner(hisUserId,hisTweetId,myName);
        return "Notification sent successfully";
    }

    @GetMapping("/sendLikeNotify")
    public String sendLikeNotify(
            @RequestParam("hisUserId") Integer hisUserId,
            @RequestParam("hisTweetId") Integer hisTweetId,
            @RequestParam("myName") String myName
    ) {
        System.out.println("/sendLikeNotify:"+hisUserId+" "+hisTweetId+" "+myName);
        tweetService.sendLikeNotificationToTweetOwner(hisUserId,hisTweetId,myName);
        return "Notification sent successfully";
    }


    @PostMapping("/changeIsRead")
    public String changeIsRead(@RequestBody Map<String,String> data) {
        Integer tweetNotiId = Integer.parseInt(data.get("tweetNotiId"));
        TweetNotification t1 = tweetService.findTweetNotificationByNotifiId(tweetNotiId);
        t1.setIsRead(1);
        tweetService.saveTweetNotification(t1);

        return "isRead 属性已修改";
    }


    @PostMapping("/updateTweetContent")
    public String saveEditedTweet(@RequestBody Map<String, Object> request) {

        String editTweetContentTmp = (String) request.get("editTweetContentTmp");
        Integer tweetId = Integer.parseInt(String.valueOf(request.get("tweetId")));

        Tweet t1 = tweetService.updateTweetContent(tweetId, editTweetContentTmp);
        if(t1 == null) {
            return "fail";
        }
        return t1.getTweetContent();
    }

    @PostMapping("/removeTweetContent")
    public Tweet removeTweetContent(@RequestBody Map<String, Object> request) {
        Integer tweetId = Integer.parseInt(String.valueOf(request.get("tweetId")));
        Tweet t1 = tweetService.findTweetByTweetId(tweetId);
        t1.setTweetStatus(0);
        return tweetService.saveTweet(t1);
    }

    @PostMapping("/reportTweet")
    public ResponseEntity<String> handlePostRequest(@RequestBody Map<String, Object> requestMap) {
        //被檢舉的tweetId
        Integer tweetId = Integer.parseInt(String.valueOf(requestMap.get("tweetId")));
        //檢舉內容(文字表單)
        String reportText = String.valueOf(requestMap.get("reportText"));
        //檢舉內容(選項)
        String reportCheckBox = String.valueOf(requestMap.get("reportCheckBox"));
        //檢舉人的UserId
        Integer userId = Integer.parseInt(String.valueOf(requestMap.get("reporterId")));

        boolean p = tweetService.checkUserAndReportRelation(tweetId, userId);
        if(p){
            return new ResponseEntity<>("You have already reported this tweet.", HttpStatus.BAD_REQUEST);
        }else{
            TweetReport tweetReport = tweetService.addReporyToTweet(tweetId, userId, reportText, reportCheckBox);
            if (tweetReport != null) {
                return new ResponseEntity<>("Tweet reported successfully!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to report tweet.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/getTweetsReports")
    public List<TweetReport> getTweetsReports() {
        return tweetService.findAllTweetReports();
    }

    @GetMapping("/getTweetByReportId/{reportId}")
    public Tweet getTweetByReportId(@PathVariable Integer reportId) {
        return tweetService.findTweetByReportId(reportId);
    }

    @GetMapping("/getUserByReportId/{reportId}")
    public UserDto getUserByReportId(@PathVariable Integer reportId) {
        UserDto userDto = new UserDto();
        userDto.setUser(tweetService.findUserByReportId(reportId));
        return userDto;
    }

}
