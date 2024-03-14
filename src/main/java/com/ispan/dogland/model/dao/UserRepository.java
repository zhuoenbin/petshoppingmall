package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUserId(Integer userId);
    @Query("select u from Users u join fetch u.dogs where u.userId = ?1")
    Users findUserAndDogsByUserId(Integer userId);
}
