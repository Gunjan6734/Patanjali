package com.patanjali.patanjaliiasclasses.model;

public class State_Model {
    private String data,value;

    public State_Model(String data, String value) {
        this.data = data;
        this.value = value;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
