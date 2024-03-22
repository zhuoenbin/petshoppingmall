package com.ispan.dogland.controller;

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

import java.util.Date;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomService rService;



    @PostMapping("/roomReservation")
    public void addRoom(@RequestBody RoomReservation roomReservation, @RequestParam Integer roomId) {
        System.out.println(roomId);
        System.out.println(roomReservation.getDogId());
        roomReservation.setRoom(rService.findByRoomId(roomId));
        rService.addRoomReservation(roomReservation);
    }

    // 顯示出全部的房間
    @GetMapping("/room")
    public List<Room> room() {
        List<Room> roomList = rService.room();

        return roomList;
    }

    // 顯示出全部的狗
    @GetMapping("/dog")
    public List<Dog> dog() {
        List<Dog> dogList = rService.dogs();
        return dogList;
    }

    // 顯示出全部的狗
    @GetMapping("/room/reservation")
    public List<List<String>> reservation() {
        List<List<String>> reservationList = rService.roomReservation();
        return reservationList;
    }




//    @PostMapping("/roomReservation")
//    public ResponseEntity<?> addRoom(@RequestBody Integer roomId,
//                                     Integer dogId,
//                                     Date startTime,
//                                     Date endTime,
//                                     Integer totalPrice,
//                                     Date reservationTime,
//                                     Date cancelTime,
//                                     String cancelDirection,
//                                     String paymentMethod,
//                                     String paymentStatus ,
//                                     HttpSession httpSession) {
//        RoomReservation roomReservation = new RoomReservation();
//        roomReservation.setRoom(rService.findByRoomId(roomId));
//        roomReservation.setDogId(dogId);
//        roomReservation.setStartTime(startTime);
//        roomReservation.setEndTime(endTime);
//        roomReservation.setTotalPrice(totalPrice);
//        roomReservation.setReservationTime(new Date());
//        roomReservation.setCancelTime(null);
//        roomReservation.setCancelDirection(null);
//        roomReservation.setPaymentMethod(paymentMethod);
//        roomReservation.setPaymentStatus(paymentStatus);
//
//        System.out.println(roomId);
//
//        rService.addRoomReservation(roomReservation);
//        return new ResponseEntity<String>("註冊成功", HttpStatusCode.valueOf(200));
//    }

}
