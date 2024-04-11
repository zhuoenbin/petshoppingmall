package com.ispan.dogland.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dao.*;
import com.ispan.dogland.model.dto.RoomReservationDto;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.room.Room;
import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.service.interfaceFile.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class RoomServicelmpl implements RoomService {

    private RoomRepository roomRepository;
    private RoomReservationRepository reservationRepository;
    private UserRepository usersRepository;
    private DogRepository dogRepository;
    private Cloudinary cloudinary;
    private MailService mailService;

    @Autowired
    public RoomServicelmpl(RoomRepository roomRepository,
                           RoomReservationRepository reservationRepository,
                           UserRepository usersRepository,
                           DogRepository dogRepository,
                           Cloudinary cloudinary,
                           MailService mailService) {
        this.roomRepository = roomRepository;
        this.usersRepository = usersRepository;
        this.reservationRepository = reservationRepository;
        this.dogRepository = dogRepository;
        this.cloudinary=cloudinary;
        this.mailService = mailService;
    }

    // 新增訂房
    @Override
    public Integer addRoomReservation(RoomReservation roomReservation,
                                   Integer roomId,
                                   Integer dogId,
                                   Integer userId) {
        roomReservation.setRoom(roomRepository.findByRoomId(roomId));
        roomReservation.setDog(dogRepository.findByDogId(dogId));
        roomReservation.setUser(usersRepository.findByUserId(userId));
        return reservationRepository.save(roomReservation).getReservationId();
    }

    // 修改訂房時間(修改訂房時間、取消訂房、評分)
    @Override
    public void updateRoomReservation(RoomReservation roomReservation,
                                      Integer roomReservationId) {
        RoomReservation reservation = reservationRepository.findByReservationId(roomReservationId);

        reservation.setStartTime(roomReservation.getStartTime());
        reservation.setEndTime(roomReservation.getEndTime());
        reservation.setTotalPrice(roomReservation.getTotalPrice());

        reservationRepository.save(reservation);
    }

    // 取消訂房
    @Override
    public void cancelRoomReservation(RoomReservation roomReservation,
                                      Integer roomReservationId) {
        RoomReservation reservation = reservationRepository.findByReservationId(roomReservationId);

        reservation.setCancelTime(roomReservation.getCancelTime());
        reservation.setCancelDirection(roomReservation.getCancelDirection());
        reservationRepository.save(reservation);
    }

    // 評分
    @Override
    public void scoreRoomReservation(RoomReservation roomReservation,
                                      Integer roomReservationId) {
        RoomReservation reservation = reservationRepository.findByReservationId(roomReservationId);

        reservation.setStar(roomReservation.getStar());
        reservation.setConments(roomReservation.getConments());
        reservation.setConmentsTime(roomReservation.getConmentsTime());

        reservationRepository.save(reservation);
    }

    // 修改房間圖片
    @Override
    public String uploadImg(@RequestParam Integer roomId, @RequestParam MultipartFile roomImgPath) {
        try{
            Map data = this.cloudinary.uploader().upload(roomImgPath.getBytes(), ObjectUtils.asMap("folder", "roomFolder"));
            Room room = roomRepository.findByRoomId(roomId);
            room.setRoomImgPath((String) data.get("url"));
            roomRepository.save(room);
            return (String) data.get("url");
        }catch (IOException e){
            throw new RuntimeException("Image uploading fail !!");
        }
    }

    // 修改房間資訊
    @Override
    public void updateRoom(Room room, Integer roomId) {
        Room room1 = roomRepository.findByRoomId(roomId);
        room1.setRoomPrice(room.getRoomPrice());
        room1.setRoomIntroduction(room.getRoomIntroduction());
        roomRepository.save(room1);
    }

    // 訂房成功寄 email
    @Override
    public void sendEmail(Integer userId, Integer roomReservationId) {
        Users user = usersRepository.findByUserId(userId);
        RoomReservation roomReservation = reservationRepository.findByReservationId(roomReservationId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // 指定日期格式
        String content = roomReservation.getUser().getLastName() + " 先生/小姐 您好" +
                "\n您在 " + sdf.format(roomReservation.getReservationTime()) + " 預訂了 " +
                roomReservation.getRoom().getRoomName() + " 號房\n入住時間: " +
                sdf.format(roomReservation.getStartTime()) + " - " + sdf.format(roomReservation.getEndTime()) +
                "\n金額: " + roomReservation.getTotalPrice();
        mailService.sendPlainText(Collections.singletonList(user.getUserEmail()), "訂房成功", content);
    }

    // 全部訂單時間
    @Override
    public List<List<String>> roomReservation() {
        List<List<String>> roomList = new ArrayList<>();

        for (RoomReservation roomReservations : reservationRepository.findAll()) {
            // 已取消的訂單不需要傳
            if(roomReservations.getCancelTime() == null) {
                LocalDate receiveDate = roomReservations.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate confirmDate = roomReservations.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // 計算日期範圍
                long daysBetween = ChronoUnit.DAYS.between(receiveDate, confirmDate);

                // 創建一個新的 LocalDate 對象，表示 receiveDate 後的第一天
                LocalDate currentDay = receiveDate.plusDays(1);
                List<String> roomTime = new ArrayList<>();
                // 加入房間id
                roomTime.add(roomReservations.getRoom().getRoomId().toString());
                // 加入訂房id
                roomTime.add(roomReservations.getReservationId().toString());
                // 加入當天日期
                roomTime.add(receiveDate.toString());

                for (int i = 0; i < daysBetween - 1; i++) {
                    roomTime.add(currentDay.plusDays(i).toString());
                }
                roomList.add(roomTime);
            }
        }
        return roomList;
    }

    // 登入者的全部訂房明細
    @Override
    public List<RoomReservationDto> findRoomReservationByUserId(Integer userId) {
        List<RoomReservationDto> roomReservationDtoList = new ArrayList<>();

        for (RoomReservation roomReservation : reservationRepository.findByUser(usersRepository.findByUserId(userId))) {

            RoomReservationDto roomReservationDto = new RoomReservationDto();

            roomReservationDto.setReservationId(roomReservation.getReservationId());
            roomReservationDto.setRoom(roomReservation.getRoom());
            roomReservationDto.setUserId(userId);
            roomReservationDto.setLastName(roomReservation.getUser().getLastName());
            roomReservationDto.setDog(roomReservation.getDog());
            roomReservationDto.setStartTime(roomReservation.getStartTime());
            roomReservationDto.setEndTime(roomReservation.getEndTime());
            roomReservationDto.setTotalPrice(roomReservation.getTotalPrice());
            roomReservationDto.setReservationTime(roomReservation.getReservationTime());
            roomReservationDto.setCancelTime(roomReservation.getCancelTime());
            roomReservationDto.setCancelDirection(roomReservation.getCancelDirection());
            roomReservationDto.setStar(roomReservation.getStar());
            roomReservationDto.setConments(roomReservation.getConments());
            roomReservationDto.setConmentsTime(roomReservation.getConmentsTime());

            roomReservationDtoList.add(roomReservationDto);
        }

        return roomReservationDtoList;
    }


    @Override
    public List<Room> findAllroom() { return roomRepository.findAll(); }

    @Override
    public List<RoomReservationDto> findAllRoomReservation() {
        List<RoomReservationDto> roomReservationDtoList = new ArrayList<>();

        for (RoomReservation roomReservation : reservationRepository.findAll()) {

            RoomReservationDto roomReservationDto = new RoomReservationDto();

            roomReservationDto.setReservationId(roomReservation.getReservationId());
            roomReservationDto.setRoom(roomReservation.getRoom());
            roomReservationDto.setUserId(roomReservation.getUser().getUserId());
            roomReservationDto.setLastName(roomReservation.getUser().getLastName());
            roomReservationDto.setDog(roomReservation.getDog());
            roomReservationDto.setStartTime(roomReservation.getStartTime());
            roomReservationDto.setEndTime(roomReservation.getEndTime());
            roomReservationDto.setTotalPrice(roomReservation.getTotalPrice());
            roomReservationDto.setReservationTime(roomReservation.getReservationTime());
            roomReservationDto.setCancelTime(roomReservation.getCancelTime());
            roomReservationDto.setCancelDirection(roomReservation.getCancelDirection());
            roomReservationDto.setStar(roomReservation.getStar());
            roomReservationDto.setConments(roomReservation.getConments());
            roomReservationDto.setConmentsTime(roomReservation.getConmentsTime());

            roomReservationDtoList.add(roomReservationDto);
        }

        return roomReservationDtoList;
    }

    @Override
    public Room findByRoomId(Integer roomId) { return roomRepository.findByRoomId(roomId); }

    @Override
    public RoomReservation findByRoomReservationId(Integer roomReservationId) { return reservationRepository.findByReservationId(roomReservationId); }

    @Override
    public RoomReservationDto findDtoByRoomReservationId(Integer roomReservationId) {
        RoomReservationDto roomReservationDto = new RoomReservationDto();
        RoomReservation roomReservation = reservationRepository.findByReservationId(roomReservationId);
        roomReservationDto.setReservationId(roomReservation.getReservationId());
        roomReservationDto.setRoom(roomReservation.getRoom());
        roomReservationDto.setUserId(roomReservation.getUser().getUserId());
        roomReservationDto.setLastName(roomReservation.getUser().getLastName());
        roomReservationDto.setDog(roomReservation.getDog());
        roomReservationDto.setStartTime(roomReservation.getStartTime());
        roomReservationDto.setEndTime(roomReservation.getEndTime());
        roomReservationDto.setTotalPrice(roomReservation.getTotalPrice());
        roomReservationDto.setReservationTime(roomReservation.getReservationTime());
        roomReservationDto.setCancelTime(roomReservation.getCancelTime());
        roomReservationDto.setCancelDirection(roomReservation.getCancelDirection());
        roomReservationDto.setStar(roomReservation.getStar());
        roomReservationDto.setConments(roomReservation.getConments());
        roomReservationDto.setConmentsTime(roomReservation.getConmentsTime());

        return roomReservationDto;
    }
}
