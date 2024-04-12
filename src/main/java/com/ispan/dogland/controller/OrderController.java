package com.ispan.dogland.controller;

import com.ispan.dogland.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/ecpayCheckout")
    public String ecpayCheckout(@RequestParam String price, @RequestParam String url) {
        System.out.println("OrderController");

        String aioCheckOutALLForm = orderService.ecpayCheckout(price, url);

        return aioCheckOutALLForm;
    }
}