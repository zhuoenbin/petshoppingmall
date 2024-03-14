package com.ispan.dogland.dao;

import com.ispan.dogland.entity.ShippingCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingCompanyRepository extends JpaRepository<ShippingCompany,Integer> {
}
