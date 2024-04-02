package com.ispan.dogland.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dto.ProductDto;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.RoomReservation;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductCategory;
import com.ispan.dogland.model.entity.product.ProductGalleryCloud;
import com.ispan.dogland.service.EmployeeService;
import com.ispan.dogland.service.RoomService;
import jakarta.servlet.http.HttpSession;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.room.Room;
import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.interfaceFile.RoomService;
import com.ispan.dogland.service.interfaceFile.TweetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://127.0.0.1:5173" })
public class EmployeeController {
    @Autowired
    private EmployeeService es;
    @Autowired
    private RoomService rService;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private TweetService tweetService;


    @GetMapping("/roomReservation")
    public List<RoomReservation> reservation(){
        return rService.findAllRoomReservation();
    }

    @GetMapping("/getCategory")
    public List<ProductCategory> getCategory(){
        return es.findCategories();
    }


    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestParam("productName") String productName,
                                             @RequestParam("categoryId") Integer categoryId,
                                             @RequestParam("productPrice") Integer unitPrices,
                                             @RequestParam("productDescription") String productDescription,
                                             @RequestParam("stock") Integer stock,
                                             @RequestParam("productImage") MultipartFile productImage,
                                             HttpSession hsession){
        try {
            Map cloudData = this.cloudinary.uploader().upload(productImage.getBytes(), ObjectUtils.asMap("folder","product"));
            String url = (String) cloudData.get("url");

            es.addNewProduct(productName,categoryId,unitPrices,productDescription,stock,url);

            return ResponseEntity.ok("Product上架成功");
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    @PostMapping("/addRoomImg")
    public String addRoomImg(Integer roomId, @RequestParam MultipartFile roomImgPath) {
        System.out.println("addRoomImg");
        System.out.println(roomId);
        System.out.println(roomImgPath);
        return rService.uploadImg(roomId, roomImgPath);
    }

    @PostMapping("/updateRoom")
    public Integer updateRoom(@RequestBody Room room, @RequestParam Integer roomId) {
        rService.updateRoom(room, roomId);
        return roomId;
    }

    @GetMapping("/banTweet/{tweetId}/{reportsId}")
    public String banTweet(@PathVariable Integer tweetId, @PathVariable Integer reportsId, HttpSession session) {
        //banTweet
        Tweet tweet = tweetService.banTweet(tweetId);
        //送通知
        Integer userId = tweet.getUser().getUserId();
        tweetService.sendBanTweetNotificationToUser(userId,tweet);
        //檢舉單號加入員工
        Passport emp = (Passport)session.getAttribute("loginUser");
        tweetService.addEmployeeToReport(reportsId,emp.getUserId());
        return emp.getUsername();
    }

    @GetMapping("/noBanTweet/{tweetId}/{reportsId}")
    public String noBanTweet(@PathVariable Integer tweetId, @PathVariable Integer reportsId, HttpSession session) {
        //檢舉單號加入員工
        Passport emp = (Passport)session.getAttribute("loginUser");
        tweetService.addEmployeeToReport(reportsId,emp.getUserId());
        return emp.getUsername();
    }

    @GetMapping("/getEmpByReportId/{reportId}")
    public Employee getEmpByReportId(@PathVariable Integer reportId) {
        return tweetService.findEmployeeByReportId(reportId);
    }

    }

}







