package com.example.nettydemo.test;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = -3288779222925023871L;
    private String id;
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
