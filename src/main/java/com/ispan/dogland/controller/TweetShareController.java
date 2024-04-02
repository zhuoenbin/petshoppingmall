package com.ispan.dogland.controller;

import com.ispan.dogland.model.dao.activity.ActivityGalleryRepository;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.activity.ActivityGallery;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import com.ispan.dogland.model.entity.room.Room;
import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.ActivityService;
import com.ispan.dogland.service.interfaceFile.AccountService;
import com.ispan.dogland.service.interfaceFile.RoomService;
import com.ispan.dogland.service.interfaceFile.TweetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

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
    @Autowired
    private RoomService roomService;

    @GetMapping("/postTweetForActivityShare")
    public String postTweetForActivityShare(@RequestParam("activityId") Integer activityId, HttpSession session) {
        Passport passport = (Passport)session.getAttribute("loginUser");
        Integer userId = passport.getUserId();

//        //user參加活動的狗
//        List<Dog> dogs = activityService.findUserDogsAttendThisActivity(userId,activityId);

        //活動title
        VenueActivity activity = activityService.findActivityByActivityId(activityId);
        String title = activity.getActivityTitle();

        //活動主圖(url_path)
        ActivityGallery gallery = activityGalleryRepository.findByVenueActivityAndGalleryImgType(activity,"main");
        String imgUrl = gallery.getGalleryImgUrl();

        //發送Tweet，如果要改推文內容，這邊改。
        String tweetContent = "我與我的狗狗報名了超讚的活動: " + title + " ，一起來參加吧 ! 立刻點擊圖片到活動頁面報名!!!";
        Tweet tweet1 = tweetService.postTweetForShare(userId,tweetContent,imgUrl);

        //發送通知
        tweetService.sendPostTweetNotificationToFollower(userId, tweet1.getTweetId());
        return null;
    }



    @GetMapping("/postTweetForRoomShare")
    public String postTweetForRoomShare(@RequestParam("reservationId") Integer reservationId, HttpSession session){
        RoomReservation roomReservation = roomService.findByRoomReservationId(reservationId);

        //房間名稱
        Integer roomName = roomReservation.getRoom().getRoomName();
        //房間圖片連結
        String imgUrl = roomReservation.getRoom().getRoomImgPath();
        //使用者Id
        Integer userId = roomReservation.getUser().getUserId();
        //狗名
        String dogName = roomReservation.getDog().getDogName();
        //評論文本
        String c = roomReservation.getConments();
        //星數
        Integer s = roomReservation.getStar();

        //發送Tweet，如果要改推文內容，這邊改。
        String tweetContent = "我的狗狗: "+dogName
                                +" 在Doggy Paradise的寵物旅館，編號 : "+roomName
                                +"住宿過後，我給它"+s+"顆星，評語："+c;
        Tweet tweet1 = tweetService.postTweetForShare(userId,tweetContent,imgUrl);

        //發送通知
        tweetService.sendPostTweetNotificationToFollower(userId, tweet1.getTweetId());
        return null;
    }
}
