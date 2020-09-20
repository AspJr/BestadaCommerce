package com.testing.bestcommerce.model;

import java.util.List;

public class ClsDeliveryForSeller {
    private String user;
    private String message;
    private List<ClsDeliveryDetailForSeller> data;

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

    public List<ClsDeliveryDetailForSeller> getData() {
        return data;
    }

    public void setData(List<ClsDeliveryDetailForSeller> data) {
        this.data = data;
    }
}
