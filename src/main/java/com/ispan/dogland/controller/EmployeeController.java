package com.ispan.dogland.controller;

import com.ispan.dogland.model.entity.room.Room;
import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.service.RoomServicelmpl;
import com.ispan.dogland.service.interfaceFile.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RoomService rService;

    @GetMapping("/roomReservation")
    public List<RoomReservation> reservation(){
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


}


