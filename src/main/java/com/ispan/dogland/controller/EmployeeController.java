package com.ispan.dogland.controller;

import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.RoomReservation;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.RoomService;
import com.ispan.dogland.service.interfaceFile.TweetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RoomService rService;

    @Autowired
    private TweetService tweetService;

    @GetMapping("/room")
    public List<RoomReservation> reservation(){
        return rService.findAllRoomReservation();
    }

    @GetMapping("/banTweet/{tweetId}/{reportsId}")
    public String banTweet(@PathVariable Integer tweetId, @PathVariable Integer reportsId, HttpSession session) {
        //banTweet
        Tweet tweet = tweetService.banTweet(tweetId);
        //送通知
        Integer userId = tweet.getUser().getUserId();
        tweetService.sendBanTweetNotificationToUser(userId,tweet);
        //檢舉單號加入員工
        Passport emp = (Passport)session.getAttribute("loginUser");
        tweetService.addEmployeeToReport(reportsId,emp.getUserId());
        return emp.getUsername();
    }

    @GetMapping("/noBanTweet/{tweetId}/{reportsId}")
    public String noBanTweet(@PathVariable Integer tweetId, @PathVariable Integer reportsId, HttpSession session) {
        //檢舉單號加入員工
        Passport emp = (Passport)session.getAttribute("loginUser");
        tweetService.addEmployeeToReport(reportsId,emp.getUserId());
        return emp.getUsername();
    }

    @GetMapping("/getEmpByReportId/{reportId}")
    public Employee getEmpByReportId(@PathVariable Integer reportId) {
        return tweetService.findEmployeeByReportId(reportId);
    }

}