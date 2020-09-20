package com.testing.bestcommerce.model;

import java.util.List;

public class ClsHistoryOrder {
    private String message;
    private List<ClsHistoryPengiriman> data_pengiriman;
    private List<ClsHistoryCheckout> data_checkout;
    private ClsHistoryPembeli nama_pembeli;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ClsHistoryPengiriman> getData_pengiriman() {
        return data_pengiriman;
    }

    public void setData_pengiriman(List<ClsHistoryPengiriman> data_pengiriman) {
        this.data_pengiriman = data_pengiriman;
    }

    public List<ClsHistoryCheckout> getData_checkout() {
        return data_checkout;
    }

    public void setData_checkout(List<ClsHistoryCheckout> data_checkout) {
        this.data_checkout = data_checkout;
    }

    public ClsHistoryPembeli getNama_pembeli() {
        return nama_pembeli;
    }

    public void setNama_pembeli(ClsHistoryPembeli nama_pembeli) {
        this.nama_pembeli = nama_pembeli;
    }
}
