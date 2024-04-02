package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.room.Room;
import com.ispan.dogland.model.entity.room.RoomReservation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RoomService {

    public Integer addRoomReservation(RoomReservation roomReservation, Integer roomId, Integer dogId, Integer userId);

    public void updateRoomReservation(RoomReservation roomReservation, Integer roomReservationId);

    public void cancelRoomReservation(RoomReservation roomReservation, Integer roomReservationId);

    public void scoreRoomReservation(RoomReservation roomReservation, Integer roomReservationId);

    public String uploadImg(@RequestParam Integer roomId, @RequestParam MultipartFile roomImgPath);

    public void updateRoom(Room room, Integer roomId);

    public void sendEmail(Integer userId, Integer roomReservationId);

    public List<List<String>> roomReservation();

    public List<RoomReservation> findRoomReservationByUserId(Integer userId);

    public List<Room> findAllroom();

    public List<RoomReservation> findAllRoomReservation();

    public Room findByRoomId(Integer roomId);

    public RoomReservation findByRoomReservationId(Integer roomReservationId);

}
