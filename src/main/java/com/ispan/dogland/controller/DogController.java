package com.ispan.dogland.controller;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.interfaceFile.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dog")
public class DogController {

    @Autowired
    private DogService dogService;

    @GetMapping("/getDogs/{userId}")
    public List<Dog> getDogs(@PathVariable Integer userId) {
        Users user = dogService.findUserAndDogsByUserId(userId);
        List<Dog> dogs = user.getDogs();
        return dogs;
    }


}
