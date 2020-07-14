package com.patanjali.patanjaliiasclasses.service;

public interface OnTaskCompleted<T> {
    void onTaskCompleted(T response);
}