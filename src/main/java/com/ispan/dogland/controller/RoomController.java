//package com.ispan.dogland.controller;
//
//import com.ispan.dogland.model.entity.Dog;
//
//import com.ispan.dogland.service.RoomService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class RoomController {
//
//    @Autowired
//    private RoomService rService;
//
//
////    訂單資訊
////    @GetMapping("/room")
////    public List<List<String>> room() {
////        List<List<String>> roomList = rService.room();
////
////        return roomList;
////    }
//
//    @GetMapping("/room")
//    public List<Room> room() {
//        List<Room> roomList = rService.room();
//
//        return roomList;
//    }
//
//    @GetMapping("/dog")
//    public List<Dog> dog() {
//        List<Dog> dogList = rService.dogs();
//
//        return dogList;
//    }
//
//}
