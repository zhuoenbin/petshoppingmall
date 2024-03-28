package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.Users;

public interface DogService {
    public Users findUserAndDogsByUserId(Integer userId);
}
