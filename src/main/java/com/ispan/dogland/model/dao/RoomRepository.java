package com.ispan.dogland.model.dao;


import com.ispan.dogland.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Integer> {
}
