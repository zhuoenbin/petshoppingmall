package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByEmail(String email);
}
