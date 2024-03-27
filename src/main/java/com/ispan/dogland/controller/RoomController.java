package com.ispan.dogland.controller;

import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Room;
import com.ispan.dogland.model.entity.RoomReservation;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService rService;

    @Autowired
    private DogRepository dRepository;

//    HttpSession session
    @GetMapping("/showUpdateRoom")
    public RoomReservation updateRoom(@RequestParam Integer roomReservationId) {
//        System.out.println(roomReservationId);
//        Passport loginUser = (Passport) session.getAttribute("loginUser");
//        loginUser.getUserId();
        return rService.findByRoomReservationId(roomReservationId);
    }

    // 修改訂單時段
    @PostMapping("/updateRoom")
    public void updateRoom(@RequestBody RoomReservation roomReservation,
                           @RequestParam Integer roomReservationId) {
        RoomReservation rr = rService.findByRoomReservationId(roomReservationId);
        rr.setStartTime(roomReservation.getStartTime());
        rr.setEndTime(roomReservation.getEndTime());
        rr.setTotalPrice(roomReservation.getTotalPrice());
        rService.addRoomReservation(rr);
    }

    // 新增訂房明細
    @PostMapping("/roomReservation")
    public void addRoom(@RequestBody RoomReservation roomReservation, @RequestParam Integer roomId, @RequestParam Integer dogId) {
        roomReservation.setRoom(rService.findByRoomId(roomId));
        roomReservation.setDog(dRepository.findByDogId(dogId));
        roomReservation.setReservationTime(new Date());
        rService.addRoomReservation(roomReservation);
    }

    // 取消訂單
    @PostMapping("/cancel")
    public void cancel(@RequestBody RoomReservation roomReservation, @RequestParam Integer roomReservationId) {
        RoomReservation rr = rService.findByRoomReservationId(roomReservationId);
        rr.setCancelTime(new Date());
        rr.setCancelDirection(roomReservation.getCancelDirection());
        rService.addRoomReservation(rr);
    }

    // 評分
    @PostMapping("/score")
    public void score(@RequestBody RoomReservation roomReservation, @RequestParam Integer roomReservationId) {
        RoomReservation rr = rService.findByRoomReservationId(roomReservationId);
        rr.setStar(roomReservation.getStar());
        rr.setConments(roomReservation.getConments());
        rr.setConmentsTime(roomReservation.getConmentsTime());
        rService.addRoomReservation(rr);
    }

    // 顯示出全部的房間
    @GetMapping("")
    public List<Room> room() {
        return rService.room();
    }

    // 顯示出全部的訂房明細
    @GetMapping("/allRoomReservationByUser")
    public List<RoomReservation> roomReservation(HttpSession session) {
        Passport loginUser = (Passport) session.getAttribute("loginUser");
        return rService.findRoomReservationByUserId(loginUser.getUserId());
    }

    // 顯示出使用者的狗
    @GetMapping("/dog")
    public List<Dog> findDogsByUsersId(HttpSession session) {
        Passport loginUser = (Passport) session.getAttribute("loginUser");
        return rService.findDogsByUsersId(loginUser.getUserId());
    }

    // 顯示全部的訂單 就算不是這個使用者的也需要
    @GetMapping("/allRoomReservation")
    public List<RoomReservation> allRoomReservation() {
        return rService.findAllRoomReservation();
    }

    // 顯示全部的訂單時間 就算不是這個使用者的也需要
    @GetMapping("/reservation")
    public List<List<String>> reservationTime() {
        return rService.roomReservation();
    }


}
