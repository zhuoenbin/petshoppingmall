package com.ispan.dogland.controller;

import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dto.*;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.activity.*;
import com.ispan.dogland.service.ActivityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
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

    //===============查詢所有活動===================
    @GetMapping("/all/{pageNumber}")
    public Page<ActivityBrief> showBriefByPage(@PathVariable Integer pageNumber){
        return activityService.findActivityByPage(pageNumber);
    }


    //===============查詢使用者的狗===================
    @GetMapping("/searchUsersDog/{userId}")
    public List<Dog> findUserDogs(@PathVariable Integer userId){
        return activityService.findUsersDog(userId);
    }

    //===============使用者與狗狗報名活動===============
    @PostMapping("/JoinActivity")
    public List<ApplyData> userDogsJoin(@RequestParam Integer userId,
                                        @RequestParam String note,
                                        @RequestParam Integer[] dogIdList,
                                        @RequestParam Integer activityId){
        VenueActivity activity = activityService.findActivityByActivityId(activityId);
        Integer activityDogNumber = activity.getActivityDogNumber();
        Integer currentDogNumber = activity.getCurrentDogNumber();
        Integer length = dogIdList.length;
        if(activityDogNumber>=currentDogNumber+length){
            ActivityUserJoined userJoined = activityService.userApply(userId, note,activityId);
            List<ApplyData> joinedMembers=new ArrayList<>();
            for(Integer dogId:dogIdList){
                ActivityDogJoined dogJoined = activityService.dogApply(dogId, activityId);
                ApplyData applyData = new ApplyData();
                BeanUtils.copyProperties(userJoined,applyData);
                BeanUtils.copyProperties(dogJoined,applyData);
                applyData.setUserId(userJoined.getUser().getUserId());
                applyData.setActivityId(dogJoined.getVenueActivity().getActivityId());
                applyData.setActivityDate(dogJoined.getVenueActivity().getActivityDate());
                applyData.setDogName(dogJoined.getDog().getDogName());
                applyData.setActivityTitle(dogJoined.getVenueActivity().getActivityTitle());
                joinedMembers.add(applyData);
            }
            return joinedMembers;
        }
        throw new RuntimeException("超過狗數限制 !!");
    }


    @GetMapping("/apply/{userId}/dogNotJoinedList/{activityId}")
    public List<Dog> findUserDogNotInThisActivity(@PathVariable Integer userId,@PathVariable Integer activityId){
        return activityService.findUserDogsStayAtHome(userId, activityId);
    }

    @GetMapping("/activityManager/{userId}/findDogListIn/{activityId}")
    public List<Dog> findUserDogsAttendThisActivity(@PathVariable Integer userId,@PathVariable Integer activityId){
        return activityService.findUserDogsAttendThisActivity(userId, activityId);
    }

    @GetMapping("/activityManager/{userId}")
    public List<MyActivitiesDto>findUserAllJoinedActivities(@PathVariable Integer userId){
        return activityService.findUserAllJoinedActivities(userId);
    }

    //===============使用者活動管理頁面取消報名===============
    @PostMapping ("/activityManager/doCancelProcess")
    public MyActivitiesDto userCancelledActivity(@RequestParam Integer userId,
                                                 @RequestParam Integer[] dogIdList,
                                                 @RequestParam Integer activityId){
        return activityService.userCancelledActivity(userId,dogIdList,activityId);
    }

    //===============使用者活動管理頁面更改備註===============
    @PostMapping ("/activityManager/doRenewNoteProcess")
    public ActivityUserJoined renewUserNote(@RequestParam Integer activityId,
                                            @RequestParam Integer userId,
                                            @RequestParam String userNote){
        return activityService.renewUserNote(activityId,userId,userNote);
    }

}
