package com.testing.bestcommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.api.MobileService;
import com.testing.bestcommerce.model.ClsAddProduct;
import com.testing.bestcommerce.model.ClsOpenShop;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadProductActivity extends AppCompatActivity {

    SharedPreferences pref;
    ProgressDialog progress;
    TextInputEditText edt_nama_product,edt_jenis_produk,edt_deskripsi,edt_harga,edt_stok;
    ImageView iv_product;
    Button btn_buka_toko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);
        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        edt_nama_product = findViewById(R.id.edt_nama_product);
        edt_jenis_produk = findViewById(R.id.edt_jenis_produk);
        edt_deskripsi = findViewById(R.id.edt_deskripsi);
        edt_harga = findViewById(R.id.edt_harga);
        edt_stok = findViewById(R.id.edt_stok);
        iv_product = findViewById(R.id.iv_product);
        btn_buka_toko = findViewById(R.id.btn_buka_toko);
        btn_buka_toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = edt_nama_product.getText().toString().trim();
                String jenis = edt_jenis_produk.getText().toString().trim();
                String deskripsi = edt_deskripsi.getText().toString().trim();
                String harga = edt_harga.getText().toString().trim();
                String stok = edt_stok.getText().toString().trim();
                if(nama.equals("") || jenis.equals("") || deskripsi.equals("") || harga.equals("") || stok.equals("")){
                    callToast("Please fill the blank");
                } else {
                    addProduct(nama,jenis,deskripsi,harga,stok);
                }
            }
        });
        setupActionBar();
    }

    private void addProduct(String nama, String jenis, String deskripsi, String harga, String stok){
        try {
            progress = new ProgressDialog(UploadProductActivity.this);
            progress.setMessage("Please Wait...");
            progress.setTitle("Inserting to server");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            ProgressBar progressbar = progress.findViewById(android.R.id.progress);
            progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0065B3"), android.graphics.PorterDuff.Mode.SRC_IN);
            MobileService service = GeneralSetting.getRetrofit();
            Call<ClsAddProduct> call = service.addProduct(pref.getInt("user_id", 0), nama, jenis, deskripsi, Float.parseFloat(harga), Integer.parseInt(stok));
            call.enqueue(new Callback<ClsAddProduct>() {
                @Override
                public void onResponse(Call<ClsAddProduct> call, Response<ClsAddProduct> response) {
                    if (response.isSuccessful()) {
                        progress.dismiss();
                        ClsAddProduct clsAddProduct = response.body();
                        callToast(clsAddProduct.getMessage());
                        Intent intent = new Intent(UploadProductActivity.this, MainSellerActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    } else {
                        callToast("Insert failed, please check connection");
                        progress.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ClsAddProduct> call, Throwable t) {
                    callToast("Failed connecting to server");
                    progress.dismiss();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Upload Product");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void callToast(String sMessage){
        Toast.makeText(getApplicationContext(),sMessage,Toast.LENGTH_SHORT).show();
    }
}