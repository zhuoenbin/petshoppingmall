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

//    // 後臺管理系統
//    @GetMapping("/employee/room")
//    public List<RoomReservation> reservation(){
////        System.out.println(rService.findAllRoomReservation());
//        return rService.findAllRoomReservation();
//    }

//    HttpSession session
    @GetMapping("/showUpdateRoom")
    public RoomReservation updateRoom(@RequestParam Integer roomReservationId) {
//        System.out.println(roomReservationId);
//        Passport loginUser = (Passport) session.getAttribute("loginUser");
//        loginUser.getUserId();
        return rService.findByRoomReservationId(roomReservationId);
    }

    // totalPrice 還沒改
    @PostMapping("/updateRoom")
    public void updateRoom(@RequestBody RoomReservation roomReservation,
                           @RequestParam Integer roomReservationId) {
        RoomReservation rr = rService.findByRoomReservationId(roomReservationId);
        rr.setStartTime(roomReservation.getStartTime());
        rr.setEndTime(roomReservation.getEndTime());
        rr.setTotalPrice(roomReservation.getTotalPrice());
        rService.addRoomReservation(rr);
    }


    @PostMapping("/roomReservation")
    public void addRoom(@RequestBody RoomReservation roomReservation, @RequestParam Integer roomId, @RequestParam Integer dogId) {
        roomReservation.setRoom(rService.findByRoomId(roomId));
        roomReservation.setDog(dRepository.findByDogId(dogId));
        roomReservation.setReservationTime(new Date());
        rService.addRoomReservation(roomReservation);
    }

    @PostMapping("/cancel")
    public void cancel(@RequestParam Integer roomReservationId,@RequestParam String cancelDirection) {
        RoomReservation rr = rService.findByRoomReservationId(roomReservationId);
        rr.setCancelTime(new Date());
        rr.setCancelDirection(cancelDirection);
        rService.addRoomReservation(rr);
    }

    // 顯示出全部的房間
    @GetMapping("")
    public List<Room> room() {
        System.out.println(rService.room());
        return rService.room();
    }

    // 顯示出全部的訂房明細
    @GetMapping("/allRoomReservation")
    public List<RoomReservation> roomReservation() {
        return rService.findAllRoomReservation();
    }

    // 顯示出全部的狗
    @GetMapping("/dog")
    public List<Dog> dog() {
        List<Dog> dogList = rService.dogs();
        return dogList;
    }

    // 顯示全部的訂單時間
    @GetMapping("/reservation")
    public List<List<String>> reservationTime() {
        List<List<String>> reservationList = rService.roomReservation();
        return reservationList;
    }



}
