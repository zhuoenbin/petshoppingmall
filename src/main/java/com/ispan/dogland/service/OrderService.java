package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.OrderDetailRepository;
import com.ispan.dogland.model.dao.OrdersRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.product.ProductGalleryRepository;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.dto.ProductDto;
import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.Orders;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductGallery;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductGalleryRepository productGalleryRepository;

    public List<OrderDetail> findDetailByOrderId(Integer orderId){
        return orderDetailRepository.findByOrderId(orderId);
    }
    public List<ProductDto> getProductsFromOrderDetails(List<Integer> productIds){
        List<Product> pList = new ArrayList<>();
        List<ProductDto> pDtoList = new ArrayList<>();
        Product p = new Product();
        List<String> imgUrls = new ArrayList<>();
        System.out.println(productIds);
        for(int i :productIds){
            System.out.println(i);
            for(ProductGallery pgs:productGalleryRepository.findByProductId(i)){
                imgUrls.add(pgs.getImgPath());
            }
            p = productRepository.findByProductId(i);
            pList.add(p);
        }
        for(Product pTmp:pList) {
            ProductDto pDto = new ProductDto();
            BeanUtils.copyProperties(pTmp, pDto);
            pDto.setMainImgPath(imgUrls.get(0));
            pDtoList.add(pDto);
        }
        System.out.println(pDtoList);
        return pDtoList;
    }

}
