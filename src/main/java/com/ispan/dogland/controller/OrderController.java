package com.ispan.dogland.controller;

import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductGallery;
import com.ispan.dogland.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://127.0.0.1:5173" })
public class OrderController {

    @Autowired
    private OrderService os;


    @GetMapping("/orderDetails")
    public List<OrderDetail> getDetailsByOrderId(HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return os.findDetailByOrderId(1);
    }

    @PostMapping("/getProducts")
    public List<Product> getProductsFromOrderDetails(@RequestBody List<Integer> productIds ,HttpSession session){
        List<Product> plist = new ArrayList<>();
        Product p = new Product();
        System.out.println(productIds);
        for(int i :productIds){
            System.out.println(i);
            p = os.findProductByProductIdInOrderDetail(i);
            plist.add(p);
        }
        System.out.println(plist);
        return plist;
    }

}
