package com.ispan.dogland.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.dto.ProductDto;
import com.ispan.dogland.model.dto.ShoppingCartDto;
import com.ispan.dogland.model.entity.ShoppingCart;
import com.ispan.dogland.model.entity.product.Product;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class ShopController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShopService shopService;
    private String allTransactionId;  //用在linepay的transactionId

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

    @PostMapping("/linepay")
    public String sendlinepay() {
        CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();
        form.setAmount(new BigDecimal("300"));  //Amount=Price*Quantity
        form.setCurrency("TWD");
        form.setOrderId("order_id1");  //這個不能一樣(範例:merchant_order_id1)

        ProductPackageForm productPackageForm = new ProductPackageForm();
        productPackageForm.setId("package_id");
        productPackageForm.setName("shop_name");
        productPackageForm.setAmount(new BigDecimal("300"));   //跟上面的Amount要一樣

        ProductForm productForm = new ProductForm();
        productForm.setId("product_id");
        productForm.setName("product_name");
        productForm.setImageUrl("");
        productForm.setQuantity(new BigDecimal("10"));
        productForm.setPrice(new BigDecimal("30"));
        productPackageForm.setProducts(Arrays.asList(productForm));
        form.setPackages(Arrays.asList(productPackageForm));

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setConfirmUrl("http://localhost:5173/shop"); //轉址的網址
        form.setRedirectUrls(redirectUrls);

        String ChannelId="2003912658";
        String ChannelSecret = "b852dee8fbe5117039966646d0ff44e5";
        String requestUri = "/v3/payments/request";
        String nonce = UUID.randomUUID().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(form);
//            SignatureUtil signatureUtil = new SignatureUtil();
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
