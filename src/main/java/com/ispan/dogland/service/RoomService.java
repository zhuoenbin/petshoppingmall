package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.RoomRepository;
import com.ispan.dogland.model.dao.RoomReservationRepository;
import com.ispan.dogland.model.entity.Dog;
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
    private RoomReservationRepository rRepository;

    @Autowired
    private DogRepository dog;

    public void addRoomReservation(RoomReservation roomReservation) {
        rRepository.save(roomReservation);
    }

//    全部訂單時間
    public List<List<String>> roomReservation() {
        List<List<String>> roomList = new ArrayList<>();

        for (RoomReservation roomReservations : rRepository.findAll()) {
            LocalDate receiveDate = roomReservations.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate confirmDate = roomReservations.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // 計算日期範圍
            long daysBetween = ChronoUnit.DAYS.between(receiveDate, confirmDate);

            // 創建一個新的 LocalDate 對象，表示 receiveDate 後的第一天
            LocalDate currentDay = receiveDate.plusDays(1);
            List<String> roomTime = new ArrayList<>();
            // 加入訂房id
            roomTime.add(roomReservations.getRoom().getRoomId().toString());
            // 加入當天日期
            roomTime.add(receiveDate.toString());

            for (int i = 0; i < daysBetween - 1; i++) {
                roomTime.add(currentDay.plusDays(i).toString());
            }

            roomList.add(roomTime);
        }

        return roomList;
    }

    public List<RoomReservation> findAllRoomReservation() {
        return rRepository.findAll();
    }

    public Room findByRoomId(Integer roomId) {
        return room.findByRoomId(roomId);
    }


    public List<Room> room() {
        List<Room> rooms = room.findAll();
        return rooms;
    }

    public List<Dog> dogs() {
        List<Dog> dogs = dog.findAll();
        return dogs;
    }

    public RoomReservation findByRoomReservationId(Integer roomReservationId) {
        return rRepository.findByReservationId(roomReservationId);
    }

}
