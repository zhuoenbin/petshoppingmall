package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Integer> {
    Dog findByDogId(Integer dogId);

    List<Dog> findByUser(Users user);
}
