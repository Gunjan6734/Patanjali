package com.patanjali.patanjaliiasclasses.model;

public class FreeDownloadModel {
    String title,type,description,url;

    public FreeDownloadModel(String title, String type, String description, String url) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
