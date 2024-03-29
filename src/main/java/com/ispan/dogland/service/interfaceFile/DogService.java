package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import org.springframework.web.multipart.MultipartFile;

public interface DogService {
    public Users findUserAndDogsByUserId(Integer userId);

    public Dog addUserDog(Dog dog, Integer userId);

    public String uploadImg(Integer dogId, MultipartFile dogImgPathCloud);


}
