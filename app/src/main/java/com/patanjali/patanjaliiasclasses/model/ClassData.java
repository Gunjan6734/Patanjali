package com.patanjali.patanjaliiasclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassData implements Serializable {
    @SerializedName("cmid")
    @Expose
    private String cmid;
    @SerializedName("goalID")
    @Expose
    private String goalID;
    @SerializedName("meetingID")
    @Expose
    private String meetingID;
    @SerializedName("classname")
    @Expose
    private String classname;
    @SerializedName("classdescription")
    @Expose
    private String classdescription;
    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("starttime")
    @Expose
    private String starttime;
    @SerializedName("classtime")
    @Expose
    private String classtime;
    @SerializedName("onlineclassfee")
    @Expose
    private String onlineclassfee;
    @SerializedName("offlineclassfee")
    @Expose
    private String offlineclassfee;
    @SerializedName("livestreaming")
    @Expose
    private String livestreaming;
    @SerializedName("offlinepayment")
    @Expose
    private String offlinepayment;
    @SerializedName("onlinepayment")
    @Expose
    private String onlinepayment;
    @SerializedName("isenquiry")
    @Expose
    private String isenquiry;
    @SerializedName("noofclassess")
    @Expose
    private String noofclassess;
    @SerializedName("onlineclassseats")
    @Expose
    private String onlineclassseats;
    @SerializedName("offlineclassseats")
    @Expose
    private String offlineclassseats;
    @SerializedName("noofdemoclasses")
    @Expose
    private String noofdemoclasses;
    @SerializedName("democlassminutes")
    @Expose
    private String democlassminutes;
    @SerializedName("bredcrumbs")
    @Expose
    private String bredcrumbs;
    @SerializedName("noofteachers")
    @Expose
    private String noofteachers;
    @SerializedName("noofsubjects")
    @Expose
    private String noofsubjects;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("notification")
    @Expose
    private String notification;
    @SerializedName("details")
    @Expose
    private ArrayList<Detail> details = null;

    public String getCmid() {
        return cmid;
    }

    public void setCmid(String cmid) {
        this.cmid = cmid;
    }

    public String getGoalID() {
        return goalID;
    }

    public void setGoalID(String goalID) {
        this.goalID = goalID;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getClassdescription() {
        return classdescription;
    }

    public void setClassdescription(String classdescription) {
        this.classdescription = classdescription;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getClasstime() {
        return classtime;
    }

    public void setClasstime(String classtime) {
        this.classtime = classtime;
    }

    public String getOnlineclassfee() {
        return onlineclassfee;
    }

    public void setOnlineclassfee(String onlineclassfee) {
        this.onlineclassfee = onlineclassfee;
    }

    public String getOfflineclassfee() {
        return offlineclassfee;
    }

    public void setOfflineclassfee(String offlineclassfee) {
        this.offlineclassfee = offlineclassfee;
    }

    public String getLivestreaming() {
        return livestreaming;
    }

    public void setLivestreaming(String livestreaming) {
        this.livestreaming = livestreaming;
    }

    public String getOfflinepayment() {
        return offlinepayment;
    }

    public void setOfflinepayment(String offlinepayment) {
        this.offlinepayment = offlinepayment;
    }

    public String getOnlinepayment() {
        return onlinepayment;
    }

    public void setOnlinepayment(String onlinepayment) {
        this.onlinepayment = onlinepayment;
    }

    public String getIsenquiry(){
        return isenquiry;
    }

    public void setIsenquiry(String isenquiry){
        this.isenquiry=isenquiry;
    }

    public String getNoofclassess() {
        return noofclassess;
    }

    public void setNoofclassess(String noofclassess) {
        this.noofclassess = noofclassess;
    }

    public String getOnlineclassseats() {
        return onlineclassseats;
    }

    public void setOnlineclassseats(String onlineclassseats) {
        this.onlineclassseats = onlineclassseats;
    }

    public String getOfflineclassseats() {
        return offlineclassseats;
    }

    public void setOfflineclassseats(String offlineclassseats) {
        this.offlineclassseats = offlineclassseats;
    }

    public String getNoofdemoclasses() {
        return noofdemoclasses;
    }

    public void setNoofdemoclasses(String noofdemoclasses) {
        this.noofdemoclasses = noofdemoclasses;
    }

    public String getDemoclassminutes() {
        return democlassminutes;
    }

    public void setDemoclassminutes(String democlassminutes) {
        this.democlassminutes = democlassminutes;
    }

    public String getBredcrumbs() {
        return bredcrumbs;
    }

    public void setBredcrumbs(String bredcrumbs) {
        this.bredcrumbs = bredcrumbs;
    }

    public String getNoofteachers() {
        return noofteachers;
    }

    public void setNoofteachers(String noofteachers) {
        this.noofteachers = noofteachers;
    }

    public String getNoofsubjects() {
        return noofsubjects;
    }

    public void setNoofsubjects(String noofsubjects) {
        this.noofsubjects = noofsubjects;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public ArrayList<Detail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<Detail> details) {
        this.details = details;
    }
}
