package com.ispan.dogland.controller;

import com.ispan.dogland.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService scService;


}
