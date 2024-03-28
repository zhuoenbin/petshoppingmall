package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    @Query("SELECT od FROM OrderDetail od JOIN od.order o ON o.orderId = :orderId")
    List<OrderDetail> findByOrderId(Integer orderId);
}
