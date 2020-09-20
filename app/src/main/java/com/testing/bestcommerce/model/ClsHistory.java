package com.testing.bestcommerce.model;

import java.util.List;

public class ClsHistory {
    private String user;
    private String message;
    private List<ClsDetailHistory> data;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ClsDetailHistory> getData() {
        return data;
    }

    public void setData(List<ClsDetailHistory> data) {
        this.data = data;
    }
}
