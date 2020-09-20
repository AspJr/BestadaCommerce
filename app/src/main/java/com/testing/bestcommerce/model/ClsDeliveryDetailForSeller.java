package com.testing.bestcommerce.model;

public class ClsDeliveryDetailForSeller {
    int id, id_checkout,id_produk,id_penjual,id_user;
    String nama_produk,image,alamat_pengiriman,kurir,order_number,created_by,updated_by,created_at,updated_at;
    float harga,sub_total,total;
    int qty,status_checkout,status_pengiriman;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_checkout() {
        return id_checkout;
    }

    public void setId_checkout(int id_checkout) {
        this.id_checkout = id_checkout;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public int getId_penjual() {
        return id_penjual;
    }

    public void setId_penjual(int id_penjual) {
        this.id_penjual = id_penjual;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAlamat_pengiriman() {
        return alamat_pengiriman;
    }

    public void setAlamat_pengiriman(String alamat_pengiriman) {
        this.alamat_pengiriman = alamat_pengiriman;
    }

    public String getKurir() {
        return kurir;
    }

    public void setKurir(String kurir) {
        this.kurir = kurir;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public float getHarga() {
        return harga;
    }

    public void setHarga(float harga) {
        this.harga = harga;
    }

    public float getSub_total() {
        return sub_total;
    }

    public void setSub_total(float sub_total) {
        this.sub_total = sub_total;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStatus_checkout() {
        return status_checkout;
    }

    public void setStatus_checkout(int status_checkout) {
        this.status_checkout = status_checkout;
    }

    public int getStatus_pengiriman() {
        return status_pengiriman;
    }

    public void setStatus_pengiriman(int status_pengiriman) {
        this.status_pengiriman = status_pengiriman;
    }
}
