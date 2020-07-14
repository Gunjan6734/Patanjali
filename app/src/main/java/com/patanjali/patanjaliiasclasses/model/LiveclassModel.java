package com.patanjali.patanjaliiasclasses.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LiveclassModel {

    @SerializedName("meeting")
    @Expose
    private String meeting;

    public String getMeeting() {
        return meeting;
    }

    public void setMeeting(String meeting) {
        this.meeting = meeting;
    }

}
