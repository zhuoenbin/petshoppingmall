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

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService rService;

    @Autowired
    private DogRepository dRepository;

    // 後臺管理系統
    @GetMapping("/employee/room")
    public List<RoomReservation> reservation(){
//        System.out.println(rService.findAllRoomReservation());
        return rService.findAllRoomReservation();
    }

    @GetMapping("/showUpdateRoom")
    public RoomReservation updateRoom(@RequestParam Integer roomReservationId, HttpSession session) {
        Passport loginUser = (Passport) session.getAttribute("loginUser");
        loginUser.getUserId();
        return rService.findByRoomReservationId(roomReservationId);
    }

    @PostMapping("/updateRoom")
    public void updateRoom() {
    }

    @PostMapping("/roomReservation")
    public void addRoom(@RequestBody RoomReservation roomReservation, @RequestParam Integer roomId, @RequestParam Integer dogId) {
        roomReservation.setRoom(rService.findByRoomId(roomId));
        roomReservation.setDog(dRepository.findByDogId(dogId));
        roomReservation.setReservationTime(new Date());
        rService.addRoomReservation(roomReservation);
    }

    // 顯示出全部的房間
    @GetMapping("/room")
    public List<Room> room() {
        System.out.println(rService.room());
        return rService.room();
    }

    // 顯示出全部的狗
    @GetMapping("/dog")
    public List<Dog> dog() {
        List<Dog> dogList = rService.dogs();
        return dogList;
    }

    // 顯示出全部的狗
    @GetMapping("/room/reservation")
    public List<List<String>> reservationTime() {
        List<List<String>> reservationList = rService.roomReservation();
        return reservationList;
    }



}
