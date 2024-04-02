package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.product.ProductCategoryRepository;
import com.ispan.dogland.model.dao.product.ProductGalleryRepository;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductCategory;
import com.ispan.dogland.model.entity.product.ProductGallery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import com.cloudinary.Cloudinary;
import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    public EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAllEmployee() { return employeeRepository.findAll(); }

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

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
        ProductGallery productGallery = new ProductGallery();
        productGallery.setImgPath(imgUrl);
        productGallery.setProduct(p);
        List<ProductGallery> pgList = new ArrayList<>();
        pgList.add(productGallery);
        p2.setProductGalleries(pgList);

        productRepository.save(p2);
    }

    public List<ProductCategory> findCategories(){
        return productCategoryRepository.findAll();
    }
}
