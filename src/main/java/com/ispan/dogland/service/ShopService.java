package com.ispan.dogland.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dao.OrderDetailRepository;
import com.ispan.dogland.model.dao.OrdersRepository;
import com.ispan.dogland.model.dao.ShoppingCartRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.dto.OderDto;
import com.ispan.dogland.model.dto.ProductDto;
import com.ispan.dogland.model.dto.ShoppingCartDto;
import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.Orders;
import com.ispan.dogland.model.entity.ShoppingCart;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductCategory;
import com.ispan.dogland.model.entity.product.ProductGallery;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    // 根據頁碼搜尋商品
    public Page<ProductDto> findProductByPage(Integer pageNumber){
        Page<Product> products = productRepository.findAll(PageRequest.of(pageNumber,6));
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

    //根據類別搜尋商品
    public Page<ProductDto> findByCategoryId(Integer pageNumber,Integer categoryId){
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Product> products = productRepository.findByCategoryId(pageable, categoryId);
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

    // 加入指定商品到指定會員的購物車
    public ShoppingCart addOneProductToCart(Integer userId,Integer productId){
        Users m = new Users(userId);
        Product p = new Product(productId);

        ShoppingCart shoppingCartItem = shoppingCartRepository.findByUsersAndProduct(m,p);

        if(shoppingCartItem !=null){
            shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + 1);
        }
        if(shoppingCartItem ==null) {
            shoppingCartItem = new ShoppingCart();
            shoppingCartItem.setUsers(m);
            shoppingCartItem.setProduct(p);
            shoppingCartItem.setQuantity(1);
        }
        return shoppingCartRepository.save(shoppingCartItem);
    }

    // 根據會員ID取得購物車
    public List<ShoppingCartDto> findShoppingCartByMemberId(Integer userId){
        List<ShoppingCart> shoppingCartItems = shoppingCartRepository.findByUsers(new Users(userId));

        List<ShoppingCartDto> shoppingCartDtos = shoppingCartItems.stream().map(c ->{
            ShoppingCartDto ct =new ShoppingCartDto();
            Product p = c.getProduct();
            BeanUtils.copyProperties(p , ct);
            BeanUtils.copyProperties(c , ct);

            List<String> imgPaths = new ArrayList<>();
            for (ProductGallery gallery : p.getProductGalleries()) {
                imgPaths.add(gallery.getImgPath());
            }
            ct.setImgPath(imgPaths);
            return ct;
        }).toList();
        return shoppingCartDtos;
    }

    //刪除指定商品以及指定會員的購物車項目
    public ShoppingCart deleteProductFromCart(Integer userId, Integer productId) {
        Users user = new Users(userId);
        Product product = new Product(productId);

        ShoppingCart shoppingCartItem = shoppingCartRepository.findByUsersAndProduct(user, product);

        if (shoppingCartItem != null) {
            shoppingCartRepository.delete(shoppingCartItem); // 找到了購物車項目，進行刪除
        }
        return null; // 返回被刪除的購物車項目，或者返回null（視情況而定）
    }

    //把userId加到oder
    public Orders addorder(Integer userId) {
        Users user = new Users(userId);
        Orders order = new Orders();
        order.setUsers(user);
        return ordersRepository.save(order);
    }

    //------------LinePay------------
    //Hmac 簽章
    public static String encrypt(final String keys, final String data) {
        return toBase64String(HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, keys.getBytes()).doFinal(data.getBytes()));
    }
    //Base64
    public static String toBase64String(byte[] bytes) {
        byte[] byteArray = Base64.encodeBase64(bytes);
        return new String(byteArray);
    }

}
