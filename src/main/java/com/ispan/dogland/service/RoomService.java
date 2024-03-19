package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.RoomRepository;
import com.ispan.dogland.model.dao.RoomReservationRepository;
import com.ispan.dogland.model.entity.Orders;
import com.ispan.dogland.model.entity.Room;
import com.ispan.dogland.model.entity.RoomReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository room;

    @Autowired
    private RoomReservationRepository roomReservation;

//    訂單時間
//    public List<List<String>> room() {
//        List<List<String>> roomList = new ArrayList<>();
//
//        for (RoomReservation roomReservations : roomReservation.findAll()) {
//            LocalDate receiveDate = roomReservations.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate confirmDate = roomReservations.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//            // 計算日期範圍
//            long daysBetween = ChronoUnit.DAYS.between(receiveDate, confirmDate);
//
//            // 創建一個新的 LocalDate 對象，表示 receiveDate 後的第一天
//            LocalDate currentDay = receiveDate.plusDays(1);
//            List<String> roomTime = new ArrayList<>();
//            // 加入訂房id
//            roomTime.add(roomReservations.getReservationId().toString());
//            // 加入當天日期
//            roomTime.add(receiveDate.toString());
//
//            // 顯示範圍內的日期
//            for (int i = 0; i < daysBetween; i++) {
//                // 加入前一天日期
//                roomTime.add(currentDay.plusDays(i).toString());
//            }
//
//            roomList.add(roomTime);
//        }
//
//        return roomList;
//    }


    public List<Room> room() {

        List<Room> rooms = room.findAll();

        return rooms;
    }

}
