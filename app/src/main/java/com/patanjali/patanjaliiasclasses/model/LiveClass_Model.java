package com.patanjali.patanjaliiasclasses.model;

public class LiveClass_Model {

    String responsecode,message;

    public LiveClass_Model(String responsecode, String message) {
        this.responsecode = responsecode;
        this.message = message;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
