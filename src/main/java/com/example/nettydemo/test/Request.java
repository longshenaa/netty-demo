package com.example.nettydemo.test;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = -3288779222925023871L;
    private String id;
    private String message;
    private byte[] attachment;

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

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }
}
