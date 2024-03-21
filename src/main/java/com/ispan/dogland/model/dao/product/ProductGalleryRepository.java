package com.ispan.dogland.model.dao.product;


import com.ispan.dogland.model.entity.product.ProductGallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGalleryRepository extends JpaRepository<ProductGallery,Integer> {
    ProductGallery findByImgId(Integer imgId);
}
