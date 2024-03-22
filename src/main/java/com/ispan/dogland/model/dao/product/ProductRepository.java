package com.ispan.dogland.model.dao.product;


import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByProductId(Integer productId);

    List<Product> findByProductName(String productName);

    @Query("from Product where category = ?1 ")
    List<Product> findByCategory(ProductCategory category);


    @Query("FROM Product p JOIN FETCH p.productGalleries WHERE p.productId = ?1")
    Product findByProductIdWithGallery(Integer productId);



}
