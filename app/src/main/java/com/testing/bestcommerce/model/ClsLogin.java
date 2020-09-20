package com.testing.bestcommerce.model;

import java.util.List;

public class ClsLogin {
    private String token;
    private boolean success;
    private int status;
    private String message;
    private List<ClsLoginDetail> user_detail;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ClsLoginDetail> getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(List<ClsLoginDetail> user_detail) {
        this.user_detail = user_detail;
    }
}
