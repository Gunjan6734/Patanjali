package com.patanjali.patanjaliiasclasses.model;

public class MorningClas_Model {

    String goalID, cmid, classname, bredcrumbs, onlineclassseats, offlineclassseats, onlineclassfee, offlineclassfee, startdate, starttime,
            classdescription, offlinepayment, livestreaming, onlinepayment,meetingID;

    public MorningClas_Model(String goalID, String cmid, String classname, String bredcrumbs, String onlineclassseats,
                             String offlineclassseats, String onlineclassfee, String offlineclassfee, String startdate,
                             String starttime, String classdescription, String offlinepayment, String livestreaming,
                             String onlinepayment, String meetingID) {
        this.goalID = goalID;
        this.cmid = cmid;
        this.classname = classname;
        this.bredcrumbs = bredcrumbs;
        this.onlineclassseats = onlineclassseats;
        this.offlineclassseats = offlineclassseats;
        this.onlineclassfee = onlineclassfee;
        this.offlineclassfee = offlineclassfee;
        this.startdate = startdate;
        this.starttime = starttime;
        this.classdescription = classdescription;
        this.offlinepayment = offlinepayment;
        this.livestreaming = livestreaming;
        this.onlinepayment = onlinepayment;
        this.meetingID = meetingID;
    }

    public String getGoalID() {
        return goalID;
    }

    public void setGoalID(String goalID) {
        this.goalID = goalID;
    }

    public String getCmid() {
        return cmid;
    }

    public void setCmid(String cmid) {
        this.cmid = cmid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getBredcrumbs() {
        return bredcrumbs;
    }

    public void setBredcrumbs(String bredcrumbs) {
        this.bredcrumbs = bredcrumbs;
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

    public String getClassdescription() {
        return classdescription;
    }

    public void setClassdescription(String classdescription) {
        this.classdescription = classdescription;
    }

    public String getOfflinepayment() {
        return offlinepayment;
    }

    public void setOfflinepayment(String offlinepayment) {
        this.offlinepayment = offlinepayment;
    }

    public String getLivestreaming() {
        return livestreaming;
    }

    public void setLivestreaming(String livestreaming) {
        this.livestreaming = livestreaming;
    }

    public String getOnlinepayment() {
        return onlinepayment;
    }

    public void setOnlinepayment(String onlinepayment) {
        this.onlinepayment = onlinepayment;
    }

    public String getMeetingID() {
        return meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }
}
