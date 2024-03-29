package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Room;
import com.ispan.dogland.model.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomReservationRepository extends JpaRepository<RoomReservation,Integer> {

    @Query("SELECT r FROM RoomReservation r ORDER BY r.startTime DESC")
    List<RoomReservation> findAll();

    RoomReservation findByReservationId(Integer reservationId);
}