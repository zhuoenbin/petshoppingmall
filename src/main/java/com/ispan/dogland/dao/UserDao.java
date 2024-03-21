package com.ispan.dogland.dao;

import com.ispan.dogland.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<Users,Integer> {
}
