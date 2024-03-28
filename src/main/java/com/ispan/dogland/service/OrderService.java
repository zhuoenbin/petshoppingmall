package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.OrderDetailRepository;
import com.ispan.dogland.model.dao.OrdersRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.product.ProductGalleryRepository;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.Orders;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductGallery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public Product findProductByProductIdInOrderDetail(Integer productId){
        return productRepository.findByProductId(productId);
    }

    public ProductGallery findPicture(Integer productId){
        return productGalleryRepository.findByProductId(productId);
    }

}
