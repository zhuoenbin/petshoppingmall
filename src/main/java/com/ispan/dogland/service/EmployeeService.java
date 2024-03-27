package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.product.ProductCategoryRepository;
import com.ispan.dogland.model.dao.product.ProductGalleryCloudRepository;
import com.ispan.dogland.model.dao.product.ProductGalleryRepository;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductCategory;
import com.ispan.dogland.model.entity.product.ProductGalleryCloud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ProductGalleryRepository productGalleryRepository;
    @Autowired
    private ProductGalleryCloudRepository productGalleryCloudRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void addNewProduct(String productName, Integer categoryId, Integer unitPrice, String productDescription, Integer stock , String imgUrl){
        Product p = new Product();
        p.setProductName(productName);
        p.setUnitPrice(unitPrice);
        p.setListingDate(new Date());
        p.setProductDescription(productDescription);
        p.setStock(stock);
        Product p2 = productRepository.save(p);

        ProductCategory pc = productCategoryRepository.findByCategoryId(categoryId);
        p2.setCategory(pc);
//        Employee e = employeeRepository.findByEmployeeId(eId);
//        p2.setEmployee(e);
        ProductGalleryCloud productGalleryCloud = new ProductGalleryCloud();
        productGalleryCloud.setCloudPath(imgUrl);
        productGalleryCloud.setProduct(p);
        List<ProductGalleryCloud> pgcList = new ArrayList<>();
        pgcList.add(productGalleryCloud);
        p2.setProductGalleryClouds(pgcList);

        productRepository.save(p2);
    }

    public List<ProductCategory> findCategories(){
        return productCategoryRepository.findAll();
    }
}
