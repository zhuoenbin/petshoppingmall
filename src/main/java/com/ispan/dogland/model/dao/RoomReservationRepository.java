package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Room;
import com.ispan.dogland.model.entity.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomReservationRepository extends JpaRepository<RoomReservation,Integer> {
    RoomReservation findByReservationId(Integer reservationId);
}