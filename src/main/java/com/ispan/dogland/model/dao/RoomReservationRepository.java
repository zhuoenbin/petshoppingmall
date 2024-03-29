package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Room;
import com.ispan.dogland.model.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomReservationRepository extends JpaRepository<RoomReservation,Integer> {

    RoomReservation findByReservationId(Integer reservationId);


    @Query("SELECT r FROM RoomReservation r WHERE r.dog.dogId = ?1")
    List<RoomReservation> findAllByDogId(Room room);
}