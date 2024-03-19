package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {

}
