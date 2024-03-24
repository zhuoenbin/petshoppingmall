package com.ispan.dogland.model.entity.activity;

import com.ispan.dogland.model.entity.Employee;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table
public class VenueActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;
    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    private String activityTitle;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date activityDate;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private Date activityStart;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private Date activityEnd;
    private String activityDescription;
    private String activityProcess;
    private String activityNotice;
    private String mainImageUrl;
    private String mainImagePublicId;
    private String mainImageData;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date activityListedDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date activityUpdateDate;
    private Integer activityStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date activityClosingDate;
    private Integer activityDogNumber;
    private Integer currentDogNumber;
    private Integer activityCost;
    private String activityCostDescription;
    private String contactInfo;
    private String contactMail;
    private String contactPhone;
    ///////////////////////////////////
    @OneToMany(mappedBy = "venueActivity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActivityGallery> galleryList;

    @OneToMany(mappedBy = "venueActivity", fetch = FetchType.LAZY)
    private List<LikedActivity>likedActivityList;

    @OneToMany(mappedBy = "venueActivity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActivityUserJoined> activityUserJoinedList;

    @OneToMany(mappedBy = "venueActivity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActivityDogJoined> activityDogJoinedList;

    ///////////////////////////////////

    @PrePersist //在物件轉換到persistent狀態以前，做這個function
    public void onCreate() {
        if (activityListedDate == null && activityUpdateDate == null) {
            activityListedDate = new Date();
            activityUpdateDate = new Date();
        }
    }

    @PreUpdate
    public void onUpdate() {
        activityUpdateDate = new Date();
    }

    ///////////////////////////////////
    public VenueActivity() {
    }
    ///////////////////////////////////


    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public Date getActivityStart() {
        return activityStart;
    }

    public void setActivityStart(Date activityStart) {
        this.activityStart = activityStart;
    }

    public Date getActivityEnd() {
        return activityEnd;
    }

    public void setActivityEnd(Date activityEnd) {
        this.activityEnd = activityEnd;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityProcess() {
        return activityProcess;
    }

    public void setActivityProcess(String activityProcess) {
        this.activityProcess = activityProcess;
    }

    public String getActivityNotice() {
        return activityNotice;
    }

    public void setActivityNotice(String activityNotice) {
        this.activityNotice = activityNotice;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getMainImagePublicId() {
        return mainImagePublicId;
    }

    public void setMainImagePublicId(String mainImagePublicId) {
        this.mainImagePublicId = mainImagePublicId;
    }

    public String getMainImageData() {
        return mainImageData;
    }

    public void setMainImageData(String mainImageData) {
        this.mainImageData = mainImageData;
    }

    public Date getActivityListedDate() {
        return activityListedDate;
    }

    public void setActivityListedDate(Date activityListedDate) {
        this.activityListedDate = activityListedDate;
    }

    public Date getActivityUpdateDate() {
        return activityUpdateDate;
    }

    public void setActivityUpdateDate(Date activityUpdateDate) {
        this.activityUpdateDate = activityUpdateDate;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Date getActivityClosingDate() {
        return activityClosingDate;
    }

    public void setActivityClosingDate(Date activityClosingDate) {
        this.activityClosingDate = activityClosingDate;
    }

    public Integer getActivityDogNumber() {
        return activityDogNumber;
    }

    public void setActivityDogNumber(Integer activityDogNumber) {
        this.activityDogNumber = activityDogNumber;
    }

    public Integer getCurrentDogNumber() {
        return currentDogNumber;
    }

    public void setCurrentDogNumber(Integer currentDogNumber) {
        this.currentDogNumber = currentDogNumber;
    }

    public Integer getActivityCost() {
        return activityCost;
    }

    public void setActivityCost(Integer activityCost) {
        this.activityCost = activityCost;
    }

    public String getActivityCostDescription() {
        return activityCostDescription;
    }

    public void setActivityCostDescription(String activityCostDescription) {
        this.activityCostDescription = activityCostDescription;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public List<ActivityGallery> getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(List<ActivityGallery> galleryList) {
        this.galleryList = galleryList;
    }

    public List<LikedActivity> getLikedActivityList() {
        return likedActivityList;
    }

    public void setLikedActivityList(List<LikedActivity> likedActivityList) {
        this.likedActivityList = likedActivityList;
    }

    public List<ActivityUserJoined> getActivityUserJoinedList() {
        return activityUserJoinedList;
    }

    public void setActivityUserJoinedList(List<ActivityUserJoined> activityUserJoinedList) {
        this.activityUserJoinedList = activityUserJoinedList;
    }

    public List<ActivityDogJoined> getActivityDogJoinedList() {
        return activityDogJoinedList;
    }

    public void setActivityDogJoinedList(List<ActivityDogJoined> activityDogJoinedList) {
        this.activityDogJoinedList = activityDogJoinedList;
    }
}
