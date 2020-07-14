package com.patanjali.patanjaliiasclasses.model;

public class Notification_Model {
    private String nid,date,notification,readStatus;

    public Notification_Model(String nid, String date, String notification, String readStatus) {
        this.nid = nid;
        this.date = date;
        this.notification = notification;
        this.readStatus = readStatus;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
}
