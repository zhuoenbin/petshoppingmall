package com.ispan.dogland.controller;

import com.ispan.dogland.model.dao.activity.ActivityGalleryRepository;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.activity.ActivityGallery;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.ActivityService;
import com.ispan.dogland.service.interfaceFile.AccountService;
import com.ispan.dogland.service.interfaceFile.TweetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetShareController {

    @Autowired
    private TweetService tweetService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityGalleryRepository activityGalleryRepository;

    @GetMapping("/postTweetForActivityShare")
    public String postTweetForActivityShare(@RequestParam("activityId") Integer activityId, HttpSession session) {
        Passport passport = (Passport)session.getAttribute("loginUser");
        Integer userId = passport.getUserId();
        //user姓名
        String username = passport.getUsername();
//        //user參加活動的狗
//        List<Dog> dogs = activityService.findUserDogsAttendThisActivity(userId,activityId);
        //活動title
        VenueActivity activity = activityService.findActivityByActivityId(activityId);
        String title = activity.getActivityTitle();
        //活動主圖(url_path)
        ActivityGallery gallery = activityGalleryRepository.findByVenueActivityAndGalleryImgType(activity,"main");
        String imgUrl = gallery.getGalleryImgUrl();
        //發送Tweet，如果要改推文內容，這邊改。
        Tweet tweet1 = tweetService.postTweetForActivityShare(userId,title,imgUrl);
        //發送通知
        tweetService.sendPostTweetNotificationToFollower(userId, tweet1.getTweetId());

        return null;
    }
}
