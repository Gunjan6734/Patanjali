package com.patanjali.patanjaliiasclasses.model;

public class VedioRecord_Model {
    String subject;
    String teacher;
    String topic;
    String date;
    String videourl;
    String presentationurl;

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    String urlType;




    public VedioRecord_Model(String subject, String teacher, String topic, String date, String videourl, String presentationurl,String urlType) {
        this.subject = subject;
        this.teacher = teacher;
        this.topic = topic;
        this.date = date;
        this.videourl = videourl;
        this.presentationurl = presentationurl;
        this.urlType=urlType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getPresentationurl() {
        return presentationurl;
    }

    public void setPresentationurl(String presentationurl) {
        this.presentationurl = presentationurl;
    }
}
