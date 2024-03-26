package com.ispan.dogland.controller;

import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dto.ActivityData;
import com.ispan.dogland.model.dto.RentalData;
import com.ispan.dogland.model.entity.activity.*;
import com.ispan.dogland.service.ActivityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/activity/api")
@CrossOrigin(allowCredentials = "true",origins = {"http://localhost:5137","http://127.0.0.1:5173"})
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    //===============列出所有venue==================
    @GetMapping("/venues")
    public List<Venue> allVenue(){
        return activityService.getAllVenue();
    }

    //===============新增活動類型===================

    @PostMapping("/type/add")
    public ActivityType createType(@RequestParam String activityType , HttpSession httpSession){
        //**之後再補只有員工能新增

        ActivityType type = activityService.searchType(activityType);
        if(activityType!=null && type==null) {
            return activityService.createType(activityType);
        }
        throw new RuntimeException("已有此活動類別");
    }

    //===============列出所有活動類型===================
    @GetMapping("/types")
    public List<ActivityType> allActivityType(){
        return activityService.allActivityType();
    }

    //===============新增場地租借===================

    @PostMapping("/rental/add")
    public RentalData createNewRental(@RequestBody RentalData rentalData){
        //**要再新增從session撈資料
        return activityService.addNewRental(rentalData);

    }
    @PostMapping("/rental/officialAdd")
    public RentalData addOfficialRental(@RequestBody RentalData rentalData){
        //**要再新增從session撈資料
        return activityService.addOfficialRental(rentalData);

    }
    //===============查詢場地租借===================
    @GetMapping("/rentals/{pageNumber}")
    public Page<RentalData> findRentalByPage(@PathVariable Integer pageNumber){
        return activityService.findRentalByPage(pageNumber);
    }
    //===============查詢使用者場地租借===================
    @GetMapping("/myRentals/{pageNumber}")
    public Page<RentalData> findRentalByPage(@PathVariable Integer pageNumber,
                                             HttpSession httpSession){
        //**session取得使用者id
        return activityService.findUserRetalByPage(1,1);
    }
    //===============官方新增活動===================

    @PostMapping("/official/addActivity")
    public ActivityData addNewActivity(@RequestBody ActivityData activityData){
        return activityService.addNewActivity(activityData);
    }

    @PostMapping("/official/addMainImg")
    public ActivityGallery addMainImg(@RequestParam Integer activityId,@RequestParam MultipartFile mainImg){
        return activityService.addTitleImg(mainImg,activityId);
    }

    @PostMapping("/official/addNormalImg")
    public List<ActivityGallery> addNormalImg(@RequestParam Integer activityId,@RequestParam MultipartFile[] normalImages){
        List<ActivityGallery> uploadedImages=new ArrayList<>();
        for(MultipartFile img:normalImages){
            ActivityGallery gallery = activityService.addNormalImg(img, activityId);
            uploadedImages.add(gallery);
        }
        return uploadedImages;
    }

    //===============所有活動===================


}
