package com.ispan.dogland.service;


import com.ispan.dogland.model.dao.OrdersRepository;
import com.ispan.dogland.model.dao.ShoppingCartRepository;
import com.ispan.dogland.model.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository scRepository;

    @Autowired
    private OrdersRepository orderRepository;

    public List<String> room(){
        List<String> String = new ArrayList<>();
        for (Orders order : orderRepository.findAll()) {
            // Convert java.util.Date to LocalDate
            LocalDate receiveDate = order.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate confirmDate = order.getUserConfirmDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Calculate days difference
            long daysDifference = ChronoUnit.DAYS.between(receiveDate, confirmDate);
            System.out.println(order.getOrderId() + " Days difference: " + daysDifference);
            String.add(order.getOrderId() + " Days difference: " + daysDifference);
        }
        return String;
    }


}
