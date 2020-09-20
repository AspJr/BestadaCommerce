package com.testing.bestcommerce.model;

import com.testing.bestcommerce.orm.tbl_pengiriman;

import java.util.List;

public class ClsCheckout {
    private String message;
    private int status;
    private List<tbl_pengiriman> data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<tbl_pengiriman> getData() {
        return data;
    }

    public void setData(List<tbl_pengiriman> data) {
        this.data = data;
    }
}
