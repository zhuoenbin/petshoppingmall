package com.ispan.dogland.controller;

import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.dto.ProductDto;
import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.Orders;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductGallery;
import com.ispan.dogland.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService os;
    @Autowired
    OrderService orderService;

    @GetMapping("")
    public List<Orders> getOrdersByUserId(HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return os.findOrdersByUserId(loggedInMember.getUserId());
    }

    @GetMapping("/{orderId}/orderDetails")
    public List<OrderDetail> getDetailsByOrderId(@PathVariable Integer orderId, HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return os.findDetailByOrderId(orderId);
    }

    @PostMapping("/getProducts")
    public List<ProductDto> getProductsFromOrderDetails(@RequestBody List<Integer> productIds , HttpSession session){
        return os.getProductsFromOrderDetails(productIds);
    }

    @PostMapping("/ecpayCheckout")
    public String ecpayCheckout(@RequestParam String price, @RequestParam String url) {
        System.out.println("OrderController");

        String aioCheckOutALLForm = orderService.ecpayCheckout(price, url);

        return aioCheckOutALLForm;
    }

//    @PostMapping("/getProducts")
//    public List<Product> getProductsFromOrderDetails(@RequestBody List<Integer> productIds ,HttpSession session){
//        List<Product> plist = new ArrayList<>();
//        Product p = new Product();
//        System.out.println(productIds);
//        for(int i :productIds){
//            System.out.println(i);
//            p = os.findProductByProductIdInOrderDetail(i);
//            plist.add(p);
//        }
//        System.out.println(plist);
//        return plist;
//    }
}