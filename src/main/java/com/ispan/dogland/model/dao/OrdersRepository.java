package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    @Query("SELECT o FROM Orders o JOIN o.user u ON u.userId = :userId")
    List<Orders> findOrdersByUserId(Integer userId);
}
