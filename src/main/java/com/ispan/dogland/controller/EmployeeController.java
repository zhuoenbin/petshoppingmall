package com.ispan.dogland.controller;

import com.ispan.dogland.model.entity.RoomReservation;
import com.ispan.dogland.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private RoomService rService;

    @GetMapping("/room")
    public List<RoomReservation> reservation(){
        return rService.findAllRoomReservation();
    }


}