package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.interfaceFile.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DogServiceImpl implements DogService {

    private DogRepository dogRepository;

    private UserRepository userRepository;
    @Autowired
    public DogServiceImpl(DogRepository dogRepository,UserRepository userRepository) {
        this.userRepository = userRepository;
        this.dogRepository = dogRepository;
    }
    @Override
    public Users findUserAndDogsByUserId(Integer userId) {
        return userRepository.findUserAndDogsByUserId(userId);
    }
}
