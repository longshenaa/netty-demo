package com.example.nettydemo.test;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = -5274690000112595967L;
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
