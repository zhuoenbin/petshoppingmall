package com.ispan.dogland.model.entity.tweet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Tweet_reports")
public class TweetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reports_id")
    private Integer reportsId;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    @JsonIgnore
    private Tweet tweet;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    @JsonIgnore
    private Users reporter;

    private String reportReason;

    private String reportDescription;

    private Date reportDate;

    private Integer reportStatus;

    public TweetReport() {
    }

    public TweetReport(Tweet tweet, Employee employee, Users reporter, String reportReason, String reportDescription, Date reportDate, Integer reportStatus) {
        this.tweet = tweet;
        this.employee = employee;
        this.reporter = reporter;
        this.reportReason = reportReason;
        this.reportDescription = reportDescription;
        this.reportDate = reportDate;
        this.reportStatus = reportStatus;
    }

    public Integer getReportsId() {
        return reportsId;
    }

    public void setReportsId(Integer reportsId) {
        this.reportsId = reportsId;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Users getReporter() {
        return reporter;
    }

    public void setReporter(Users reporter) {
        this.reporter = reporter;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TweetReport{");
        sb.append("reportsId=").append(reportsId);
        sb.append(", reportReason='").append(reportReason).append('\'');
        sb.append(", reportDescription='").append(reportDescription).append('\'');
        sb.append(", reportDate=").append(reportDate);
        sb.append(", reportStatus=").append(reportStatus);
        sb.append('}');
        return sb.toString();
    }
}