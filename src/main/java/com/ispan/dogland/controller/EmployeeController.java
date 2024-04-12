package com.ispan.dogland.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.dto.RoomReservationDto;
import com.ispan.dogland.model.entity.Employee;
//import com.ispan.dogland.model.entity.mongodb.TweetData;
import com.ispan.dogland.model.entity.room.Room;
import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.interfaceFile.RoomService;
import com.ispan.dogland.service.interfaceFile.TweetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RoomService rService;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/roomReservation")
    public List<RoomReservationDto> reservation(){
        return rService.findAllRoomReservation();
    }

    @PostMapping("/addRoomImg")
    public String addRoomImg(Integer roomId, @RequestParam MultipartFile roomImgPath) {
        System.out.println("addRoomImg");
        System.out.println(roomId);
        System.out.println(roomImgPath);
        return rService.uploadImg(roomId, roomImgPath);
    }

    @PostMapping("/updateRoom")
    public Integer updateRoom(@RequestBody Room room, @RequestParam Integer roomId) {
        rService.updateRoom(room, roomId);
        return roomId;
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



    @GetMapping("/sendTweetContentsToFlask")
    @Async
    public String sendToFlask() {
        // Flask 應用程式的 URL
        String flaskUrl = "http://localhost:5000/api/process_sentence";
        // 設置headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 準備請求內容
        List<Tweet> tweets = tweetService.findAllTweetsOnly();
        List<String> sentences = new ArrayList<>();
        for (Tweet tweet : tweets) {
            sentences.add(tweet.getTweetContent());
        }
        // 創建 ObjectMapper 實例
        ObjectMapper objectMapper = new ObjectMapper();
        // 將 sentences 轉換為 JSON 字符串
        String sentencesJson;
        try {
            sentencesJson = objectMapper.writeValueAsString(sentences);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 處理序列化時的異常
            return "Error occurred during JSON serialization";
        }
        String requestBody = "{\n" +
                "    \"sentences\": " + sentencesJson + "\n" +
                "}";
        // 發送 POST 請求到 Flask 應用程式
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String result = restTemplate.postForObject(flaskUrl, requestEntity, String.class);
        // 回傳 Flask 的回應
        return result;
    }


//    @GetMapping("/getTweetData")
//    public TweetData getTweetData() {
//        return tweetService.getLastTweetData();
//    }
}


