package com.ispan.dogland.controller;

import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.dto.ProductDto;
import com.ispan.dogland.model.dto.ShoppingCartDto;
import com.ispan.dogland.model.entity.ShoppingCart;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductGallery;
import com.ispan.dogland.service.ShopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ShopController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShopService shopService;

    //商品列表與商品分頁
    @GetMapping("/products/{pageNumber}")
    public Page<ProductDto> findAllProducts(@PathVariable Integer pageNumber){
        Page<Product> products = productRepository.findAll(PageRequest.of(pageNumber,2));
        Page<ProductDto> productDtos = products.map(p -> {
            ProductDto pto = new ProductDto();
            BeanUtils.copyProperties(p, pto);

            // 獲取產品的所有圖片路徑列表
            List<String> imgPaths = new ArrayList<>();
            for (ProductGallery gallery : p.getProductGalleries()) {
                imgPaths.add(gallery.getImgPath());
            }
            pto.setImgPath(imgPaths);
            return pto;
        });
        return productDtos;
    }

    //加入購物車
    @RequestMapping("/product/add/{productId}")
    public ShoppingCart addOneProductToCart(@PathVariable Integer productId, HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return shopService.addOneProductToCart(loggedInMember.getUserId(),productId);
    }

    @RequestMapping("/cart")
    public List<ShoppingCartDto> getCartByMemberId( HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        List<ShoppingCartDto> cartItems = shopService.findShoppingCartByMemberId(loggedInMember.getUserId());
        return cartItems;
    }

    //刪除購物車
    @RequestMapping("/product/delete/{productId}")
    public ShoppingCart deleteProductFromCart(@PathVariable Integer productId, HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return shopService.deleteProductFromCart(loggedInMember.getUserId(),productId);
    }

}
