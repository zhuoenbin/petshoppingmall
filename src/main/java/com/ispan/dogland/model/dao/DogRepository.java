package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<Dog, Integer> {
    Dog findByDogId(Integer dogId);
}