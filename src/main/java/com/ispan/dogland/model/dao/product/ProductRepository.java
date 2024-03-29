package com.ispan.dogland.model.dao.product;


import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductCategory;
import jdk.jfr.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
//    Product findByProductId(Integer productId);
//    List<Product> findByProductName(String productName);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
    Page<Product> findByCategoryId(Pageable pageable,Integer categoryId);

}
