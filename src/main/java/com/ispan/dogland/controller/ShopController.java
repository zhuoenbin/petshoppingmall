package com.ispan.dogland.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.dogland.model.dao.OrderDetailRepository;
import com.ispan.dogland.model.dao.OrdersRepository;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.dto.OderDto;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.dto.ProductDto;
import com.ispan.dogland.model.dto.ShoppingCartDto;
import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.Orders;
import com.ispan.dogland.model.entity.ShoppingCart;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductCategory;
import com.ispan.dogland.model.entity.product.ProductGallery;
import com.ispan.dogland.model.vo.CheckoutPaymentRequestForm;
import com.ispan.dogland.model.vo.ProductForm;
import com.ispan.dogland.model.vo.ProductPackageForm;
import com.ispan.dogland.model.vo.RedirectUrls;
import com.ispan.dogland.service.ShopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@RestController
public class ShopController {

    private String allTransactionId;  //用在linepay的transactionId
    @Autowired
    private ShopService shopService;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;

    //商品列表與商品分頁(沒有加關鍵字查詢)目前沒用到
    @GetMapping("/products/{pageNumber}")
    public Page<ProductDto> findProductByPage(@PathVariable Integer pageNumber){
        Page<ProductDto> productDtos = shopService.findProductByPage(pageNumber);
        return productDtos;
    }

    //商品列表與商品分頁(增加搜尋功能)
    @GetMapping("/products-keyword/{pageNumber}")
    public Page<ProductDto> findProductByPageWithKeyword(@PathVariable Integer pageNumber,
                                                         @RequestParam(required = false) String keyword) {
        Page<ProductDto> productDtos = shopService.findProductByPageWithKeyword(pageNumber, keyword);
        return productDtos;
    }

    //用類別查詢商品
    @GetMapping("/category/{pageNumber}/{categoryId}")
    public Page<ProductDto> findByCategory(@PathVariable Integer pageNumber,
                                           @PathVariable Integer categoryId) {
        Page<ProductDto> products = shopService.findByCategoryId(pageNumber, categoryId);
        return products;
    }

    //根據productID回傳商品
    @GetMapping("/productPage/{productId}")
    public List<ProductDto> findProductByPageWithKeyword(@PathVariable Integer productId) {
        List<ProductDto> productDtos = shopService.findByProductPage(productId);
        return productDtos;
    }
    
    //加入購物車(ShopPage)
//    @RequestMapping("/product/add/{productId}")
//    public ShoppingCart addOneProductToCart(@PathVariable Integer productId, HttpSession session){
//        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
//        if(loggedInMember == null){
//            throw new RuntimeException("未登入錯誤");
//        }
//        return shopService.addOneProductToCart(loggedInMember.getUserId(),productId);
//    }

//    //加入購物車(ProductPage)
//    @RequestMapping("/productPage/add/{productId}/{quantity}")
//    public ShoppingCart pageAddToCart(@PathVariable Integer productId,
//                                      @PathVariable Integer quantity,
//                                      HttpSession session){
//        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
//        if(loggedInMember == null){
//            throw new RuntimeException("未登入錯誤");
//        }
//        return shopService.pageAddToCart(loggedInMember.getUserId(),productId,quantity);
//    }

    ///加入購物車(totalAddToCart)
    @RequestMapping("/totalAddToCart/add/{productId}")
    public ShoppingCart totalAddToCart(@PathVariable Integer productId,
                                       @RequestParam(required = false) Integer quantity,
                                      HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return shopService.totalAddToCart(loggedInMember.getUserId(),productId,quantity);
    }

    //拿到購物車的商品
    @RequestMapping("/cart")
    public List<ShoppingCartDto> getCartByMemberId( HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
//        System.out.println(loggedInMember);
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

    //加入訂單
    @PostMapping("/order")
    public void getCartData(@RequestBody List<OderDto> oderDtos, HttpSession session) {
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        Orders order = shopService.addorder(loggedInMember.getUserId());

        for (OderDto oderDto : oderDtos) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(order);
            BeanUtils.copyProperties(oderDto, orderDetail);
            orderDetailRepository.save(orderDetail);
        }
    }

    @PostMapping("/linepay/{totalPrice}")
    public String sendlinepay(@PathVariable String totalPrice) {
        CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();

        form.setAmount(new BigDecimal(totalPrice));  //Amount=Price*Quantity
        form.setCurrency("TWD");
        form.setOrderId(UUID.randomUUID().toString());  //這個不能一樣(order_id)先用UUID

        ProductPackageForm productPackageForm = new ProductPackageForm();
        productPackageForm.setId(UUID.randomUUID().toString()); //自動生成package_id(先用UUID)
        productPackageForm.setName("dogshop");
        productPackageForm.setAmount(new BigDecimal(totalPrice));   //跟上面的Amount要一樣

        ProductForm productForm = new ProductForm();
        productForm.setId("product_id");
        productForm.setName("Dog Shop");
        productForm.setImageUrl("");
        productForm.setQuantity(new BigDecimal("1"));
        productForm.setPrice(new BigDecimal(totalPrice));

        productPackageForm.setProducts(Arrays.asList(productForm));
        form.setPackages(Arrays.asList(productPackageForm));

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setConfirmUrl("http://localhost:5173/shop/shopPage"); //轉址的網址
        form.setRedirectUrls(redirectUrls);

        String ChannelId="2003912658";
        String ChannelSecret = "b852dee8fbe5117039966646d0ff44e5";
        String requestUri = "/v3/payments/request";
        String nonce = UUID.randomUUID().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(form);
            String signature = ShopService.encrypt(ChannelSecret, ChannelSecret + requestUri + requestBody + nonce);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-LINE-ChannelId", ChannelId);
            headers.set("X-LINE-Authorization-Nonce", nonce);
            headers.set("X-LINE-Authorization", signature);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            String linePayApiUrl = "https://sandbox-api-pay.line.me/v3/payments/request";
            String response = restTemplate.postForObject(linePayApiUrl, requestEntity, String.class);

            JsonNode jsonResponse = objectMapper.readTree(response);
            String paymentUrl = jsonResponse.get("info").get("paymentUrl").get("web").asText();
            String transactionId = jsonResponse.get("info").get("transactionId").asText();
            allTransactionId = transactionId;
            System.out.println("response => " + response);
            System.out.println("transactionId =>" + transactionId);

            return paymentUrl;  // 轉址到獲取的 URL
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null; // 如果發生異常，返回 null 或者其他適當的值
    }

}
