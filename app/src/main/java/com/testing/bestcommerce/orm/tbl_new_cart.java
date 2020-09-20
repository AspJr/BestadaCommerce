package com.testing.bestcommerce.orm;

import com.orm.SugarRecord;

public class tbl_new_cart extends SugarRecord {
    public int id_produk;
    public int id_penjual;
    public int id_user;
    public String nama_produk;
    public String img;
    public String harga;
    public int qty;
    public float sub_total;
    public float total;
    public String alamat_pengiriman;
    public String kurir;
    public String created_by;
    public String created_date;
    public String modified_by;
    public String modified_date;

    public tbl_new_cart() {
    }

    public tbl_new_cart(int id_produk, int id_penjual, int id_user, String nama_produk, String img, String harga, int qty, float sub_total, float total, String alamat_pengiriman, String kurir, String created_by, String created_date, String modified_by, String modified_date) {
        this.id_produk = id_produk;
        this.id_penjual = id_penjual;
        this.id_user = id_user;
        this.nama_produk = nama_produk;
        this.img = img;
        this.harga = harga;
        this.qty = qty;
        this.sub_total = sub_total;
        this.total = total;
        this.alamat_pengiriman = alamat_pengiriman;
        this.kurir = kurir;
        this.created_by = created_by;
        this.created_date = created_date;
        this.modified_by = modified_by;
        this.modified_date = modified_date;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
}
