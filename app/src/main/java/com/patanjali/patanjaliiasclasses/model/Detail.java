package com.patanjali.patanjaliiasclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Detail implements Serializable {
    @SerializedName("cdid")
    @Expose
    private String cdid;
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("noofclassess")
    @Expose
    private String noofclassess;
    @SerializedName("classduration")
    @Expose
    private String classduration;
    @SerializedName("message")
    @Expose
    private String message;

    public String getCdid() {
        return cdid;
    }

    public void setCdid(String cdid) {
        this.cdid = cdid;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNoofclassess() {
        return noofclassess;
    }

    public void setNoofclassess(String noofclassess) {
        this.noofclassess = noofclassess;
    }

    public String getClassduration() {
        return classduration;
    }

    public void setClassduration(String classduration) {
        this.classduration = classduration;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
