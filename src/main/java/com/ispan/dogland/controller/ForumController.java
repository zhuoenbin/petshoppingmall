package com.ispan.dogland.controller;

import com.ispan.dogland.model.entity.forum.Articles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ForumController {

    @GetMapping("/fourm")
    List<Articles> fourmHome(){



        return null;
    }
}
