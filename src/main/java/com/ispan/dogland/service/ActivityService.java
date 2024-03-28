package com.ispan.dogland.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.activity.*;
import com.ispan.dogland.model.dto.ActivityBrief;
import com.ispan.dogland.model.dto.ActivityData;
import com.ispan.dogland.model.dto.RentalData;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.activity.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActivityService {
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private VenueRentalRepository rentalRepository;
    @Autowired
    private VenueActivityRepository activityRepository;
    @Autowired
    private LikedActivityRepository likedRepository;
    @Autowired
    private ActivityTypeRepository typeRepository;
    @Autowired
    private ActivityGalleryRepository galleryRepository;
    @Autowired
    private ActivityDogJoinedRepository dogJoinedRepository;
    @Autowired
    private  ActivityUserJoinedRepository userJoinedRepository;

    //////////////////////場地////////////////////////

    //===============尋找venue==================
    public Venue getVenueByName(String venueName){
        return venueRepository.findByVenueName(venueName);
    }
    public Venue getVenueById(Integer venueId){
        return venueRepository.findByVenueId(venueId);
    }

    //===============列出所有venue==================
    public List<Venue> getAllVenue(){
        return venueRepository.findAll();
    }

    ///////////////////////活動類型/////////////////////////

    //===============新增活動類型===================
    public ActivityType createType(String activityType){
        ActivityType type = new ActivityType();
        type.setActivityTypeName(activityType);
        return typeRepository.save(type);
    }

    //===============查詢活動類型===================
    public  ActivityType searchType(String activityType){
        return typeRepository.findByActivityTypeName(activityType);
    }

    //===============列出所有活動類型===================
    public List<ActivityType> allActivityType(){
        return typeRepository.findAll();
    }


    ///////////////////////場地租借/////////////////////////
    //===============新增場地租借訂單===================
    public RentalData addNewRental(RentalData rentalData){
        Integer venueId = rentalData.getVenueId();
        Integer userId = rentalData.getUserId();
        Users user = userRepository.findByUserId(userId);
        Venue venue = venueRepository.findByVenueId(venueId);
        VenueRental rental = new VenueRental();
        BeanUtils.copyProperties(rentalData,rental);
        rental.setUser(user);
        rental.setVenue(venue);
        VenueRental save = rentalRepository.save(rental);

        RentalData newData = new RentalData();
        BeanUtils.copyProperties(save,newData);
        newData.setUserId(save.getUser().getUserId());
        newData.setVenueId(save.getVenue().getVenueId());
        return newData;
    }
    public RentalData addOfficialRental(RentalData rentalData){
        Integer venueId = rentalData.getVenueId();


        Venue venue = venueRepository.findByVenueId(venueId);
        VenueRental rental = new VenueRental();
        BeanUtils.copyProperties(rentalData,rental);
        rental.setUser(null);
        rental.setVenue(venue);
        VenueRental save = rentalRepository.save(rental);

        RentalData newData = new RentalData();
        BeanUtils.copyProperties(save,newData);
        newData.setUserId(null);
        newData.setVenueId(save.getVenue().getVenueId());
        return newData;
    }

    //===============查詢全部場地租借訂單===================

    public Page<RentalData> findRentalByPage(Integer pageNumber){
        Page<VenueRental> rental = rentalRepository.findAll(PageRequest.of(pageNumber, 9));
        System.out.println(rental.getTotalElements());
        Page<RentalData> rentalDataList = rental.map(r -> {
                RentalData rentalData = new RentalData();
                BeanUtils.copyProperties(r, rentalData);
                rentalData.setVenueId(r.getVenue().getVenueId());
                if (r.getUser() != null) {
                    rentalData.setUserId(r.getUser().getUserId());
                }else{
                    rentalData.setUserId(0);
                }
                return rentalData;
            });
            return rentalDataList;
    }

    //===============查詢某使用者場地租借訂單===================
    public Page<RentalData> findUserRetalByPage(Integer userId,Integer pageNumber){
        Users user=userRepository.findByUserId(userId);
        Page<VenueRental> rental = rentalRepository.findByUser(user,PageRequest.of(pageNumber, 9));
        System.out.println(rental.getTotalElements());
        Page<RentalData> rentalDataList = rental.map(r -> {
            RentalData rentalData = new RentalData();
            BeanUtils.copyProperties(r, rentalData);
            rentalData.setVenueId(r.getVenue().getVenueId());
            if (r.getUser() != null) {
                rentalData.setUserId(r.getUser().getUserId());
            }
            return rentalData;
        });
        return rentalDataList;
    }
    //===============查詢官方場地租借訂單===================
    public Page<RentalData> findOfficialRentalByPage(Integer pageNumber){
        Page<VenueRental> officialRental = rentalRepository.findByUserNull(PageRequest.of(pageNumber, 9));
        System.out.println(officialRental.getTotalElements());
        Page<RentalData> rentalDataList=officialRental.map(r->{
            RentalData rentalData = new RentalData();
            BeanUtils.copyProperties(r,rentalData);
            rentalData.setVenueId((r.getVenue().getVenueId()));
            rentalData.setUserId(0);
            return rentalData;
        });
        return rentalDataList;
    }


    ///////////////////////場地活動/////////////////////////
    //===============新增場地活動===================
    public ActivityData addNewActivity(ActivityData activityData){
        Integer typeId = activityData.getActivityTypeId();
        ActivityType type = typeRepository.findByActivityTypeId(typeId);
        Integer employeeId = activityData.getEmployeeId();
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Integer venueId = activityData.getVenueId();
        Venue venue = venueRepository.findByVenueId(venueId);
        VenueActivity activity = new VenueActivity();
        BeanUtils.copyProperties(activityData,activity);
        activity.setEmployee(employee);
        activity.setVenue(venue);
        activity.setActivityType(type);
        VenueActivity save = activityRepository.save(activity);

        ActivityData newData = new ActivityData();
        BeanUtils.copyProperties(save,newData);
        newData.setActivityId(save.getActivityId());
        newData.setEmployeeId(save.getEmployee().getEmployeeId());
        newData.setActivityTypeId(save.getActivityType().getActivityTypeId());
        newData.setVenueId(save.getVenue().getVenueId());
        return  newData;
    }
    //===============新增活動照片===================
    public ActivityGallery addTitleImg(MultipartFile file,Integer activityId){
        //上傳到producutFolder裡
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "activityFolder"));
            VenueActivity activity = activityRepository.findByActivityId(activityId);

            ActivityGallery gallery = new ActivityGallery();
            gallery.setVenueActivity(activity);
            gallery.setGalleryImgUrl((String) data.get("url"));
            gallery.setGalleryPublicId((String) data.get("public_id"));
            gallery.setGalleryImgType("main");

            System.out.println("活動id: "+activityId+" 的主題照片上傳成功!");

            activity.setActivityUpdateDate(new Date());
            activityRepository.save(activity);

            return galleryRepository.save(gallery);
        } catch (IOException e) {
            throw new RuntimeException("Image uploading fail !!");
        }
    }

    public ActivityGallery addNormalImg(MultipartFile file,Integer activityId){
        //上傳到producutFolder裡
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "activityFolder"));
            VenueActivity activity = activityRepository.findByActivityId(activityId);

            ActivityGallery gallery = new ActivityGallery();
            gallery.setVenueActivity(activity);
            gallery.setGalleryImgUrl((String) data.get("url"));
            gallery.setGalleryPublicId((String) data.get("public_id"));
            gallery.setGalleryImgType(null);

            System.out.println("活動id: "+activityId+" 的其他說明照片上傳成功!");

            activity.setActivityUpdateDate(new Date());
            activityRepository.save(activity);

            return galleryRepository.save(gallery);
        } catch (IOException e) {
            throw new RuntimeException("Image uploading fail !!");
        }
    }

    //===============所有活動簡述===============
    public Page<ActivityBrief> findActivityByPage(Integer pageNumber){
        Page<VenueActivity> all = activityRepository.findAll(PageRequest.of(pageNumber-1, 4));

        Page<ActivityBrief> briefs = all.map(a->{
            ActivityBrief ab = new ActivityBrief();
            BeanUtils.copyProperties(a,ab);

            Integer activityId = a.getActivityId();
            VenueActivity activity = activityRepository.findByActivityId(activityId);
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");
            ab.setGalleryImgUrl(main.getGalleryImgUrl());

            ab.setActivityTypeName(a.getActivityType().getActivityTypeName());
            ab.setVenueName(a.getVenue().getVenueName());
            return ab;
        });
        return briefs;
    }

    //===============依類別找活動簡述===============
    public Page<ActivityBrief> findActivityByType(Integer typeId, Integer pageNumber){
        ActivityType type = typeRepository.findByActivityTypeId(typeId);
        Page<VenueActivity> activities = activityRepository.findByActivityType(type,PageRequest.of(pageNumber-1, 4));
        System.out.println(activities.getTotalElements());
        Page<ActivityBrief> activityDataList = activities.map(r -> {
            ActivityBrief brief = new ActivityBrief();
            BeanUtils.copyProperties(r, brief);
            brief.setActivityTypeName(r.getActivityType().getActivityTypeName());
            brief.setVenueName(r.getVenue().getVenueName());

            Integer activityId = r.getActivityId();
            VenueActivity activity = activityRepository.findByActivityId(activityId);
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");

            brief.setGalleryImgUrl(main.getGalleryImgUrl());
            return brief;
        });
        return activityDataList;
    }







}
