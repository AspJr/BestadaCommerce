package com.testing.bestcommerce.model;

import java.util.List;

public class ClsProduct {
    String message;
    private List<ClsProductDetail> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ClsProductDetail> getData() {
        return data;
    }

    public void setData(List<ClsProductDetail> data) {
        this.data = data;
    }
}
