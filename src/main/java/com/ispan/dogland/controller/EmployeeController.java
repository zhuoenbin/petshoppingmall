package com.ispan.dogland.controller;

import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Room;
import com.ispan.dogland.model.entity.RoomReservation;
import com.ispan.dogland.service.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RoomService rService;

    @GetMapping("/room")
    public List<RoomReservation> reservation(){
//        System.out.println(rService.findAllRoomReservation());
        return rService.findAllRoomReservation();
    }


}
