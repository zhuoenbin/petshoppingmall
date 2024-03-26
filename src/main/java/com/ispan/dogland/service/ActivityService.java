package com.ispan.dogland.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.activity.*;
import com.ispan.dogland.model.dto.ActivityData;
import com.ispan.dogland.model.dto.RentalData;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.activity.ActivityType;
import com.ispan.dogland.model.entity.activity.Venue;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import com.ispan.dogland.model.entity.activity.VenueRental;

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
    public RentalData createRentalOrder(VenueRental venueRental, Integer userId,Integer venueId){
        RentalData rentalData = new RentalData();
        Venue venue = venueRepository.findByVenueId(venueId);
        venueRental.setVenue(venue);
        //使用者訂單
        if(userId!=null) {
            Users user = userRepository.findByUserId(userId);
            venueRental.setUser(user);

            //計算價錢
            Date rentalEnd = venueRental.getRentalEnd();
            Date rentalStart = venueRental.getRentalStart();
            Integer hour= (int)Math.ceil(rentalEnd.getTime() - rentalStart.getTime())/ (1000 * 60 * 60); // 毫秒轉換為小時;

            //取得價目
            Integer venueRent = venueRental.getVenue().getVenueRent();
            Integer total=venueRent*hour;
            venueRental.setRentalTotal(total);

            //存入訂單
            VenueRental saveCustomer = rentalRepository.save(venueRental);

            //存入要給前端的dto
            BeanUtils.copyProperties(saveCustomer, rentalData);
            rentalData.setUserId(saveCustomer.getUser().getUserId());
            rentalData.setVenueId(saveCustomer.getVenue().getVenueId());
            return rentalData;
        }

        //官方訂單
        venueRental.setPaymentStatus(2);
        venueRental.setRentalTotal(0);
        VenueRental saveOfficial = rentalRepository.save(venueRental);
        BeanUtils.copyProperties(saveOfficial,rentalData);
        rentalData.setVenueId(saveOfficial.getVenue().getVenueId());
        return rentalData;
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

    ///////////////////////場地活動/////////////////////////
    //===============新增場地活動===================
    public ActivityData createNewActivity(VenueActivity venueActivity,
                                          Integer activityTypeId,
                                          Integer venueId,
                                          Integer employeeId){
        ActivityData activityData = new ActivityData();

            Employee employee = employeeRepository.findByEmployeeId(employeeId);
            ActivityType type = typeRepository.findByActivityTypeId(activityTypeId);
            Venue venue = venueRepository.findByVenueId(venueId);

            venueActivity.setEmployee(employee);
            venueActivity.setVenue(venue);
            venueActivity.setActivityType(type);

            VenueActivity activity = activityRepository.save(venueActivity);

            BeanUtils.copyProperties(activity, activityData);
            activityData.setEmployeeId(activity.getEmployee().getEmployeeId());
            activityData.setVenueId(activity.getVenue().getVenueId());
            activityData.setActivityTypeId(activity.getActivityType().getActivityTypeId());
            return activityData;
    }








}
