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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.api.MobileService;
import com.testing.bestcommerce.model.ClsCheckShop;
import com.testing.bestcommerce.model.ClsOpenShop;
import com.testing.bestcommerce.model.ClsProduct;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenShopActivity extends AppCompatActivity {

    SharedPreferences pref;
    TextInputEditText edt_nama_toko, edt_alamat;
    Button btn_buka_toko;
    ProgressDialog progress;
    RelativeLayout progress_master,relativeBukaToko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_shop);
        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        progress_master = findViewById(R.id.progress_master);
        relativeBukaToko = findViewById(R.id.relativeBukaToko);
        edt_nama_toko = findViewById(R.id.edt_nama_toko);
        edt_alamat = findViewById(R.id.edt_alamat);
        btn_buka_toko = findViewById(R.id.btn_buka_toko);
        btn_buka_toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = edt_nama_toko.getText().toString().trim();
                String alamat = edt_alamat.getText().toString().trim();
                if (nama.equals("") || alamat.equals("")){
                    callToast("Please fill the blank");
                } else {
                    openShop(nama, alamat);
                }
            }
        });
        setupActionBar();
        checkShop();
    }

    void checkShop(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            Call<ClsCheckShop> call = GeneralSetting.getRetrofit().checkShop(pref.getInt("user_id", 0));
            call.enqueue(new Callback<ClsCheckShop>() {
                @Override
                public void onResponse(Call<ClsCheckShop> call, Response<ClsCheckShop> response) {
                    if (response.isSuccessful()){
                        ClsCheckShop data = response.body();
                        if(data.getStatus() == 1){
                            Intent intent = new Intent(OpenShopActivity.this, MainSellerActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(intent);
                        } else {
                            progress_master.setVisibility(View.GONE);
                            relativeBukaToko.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to get data");
                    }
                }

                @Override
                public void onFailure(Call<ClsCheckShop> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openShop(String nama, String alamat){
        try {
            progress = new ProgressDialog(OpenShopActivity.this);
            progress.setMessage("Please Wait...");
            progress.setTitle("Inserting to server");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            ProgressBar progressbar = progress.findViewById(android.R.id.progress);
            progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0065B3"), android.graphics.PorterDuff.Mode.SRC_IN);
            MobileService service = GeneralSetting.getRetrofit();
            Call<ClsOpenShop> call = service.openShop(pref.getInt("user_id", 0), nama, alamat);
            call.enqueue(new Callback<ClsOpenShop>() {
                @Override
                public void onResponse(Call<ClsOpenShop> call, Response<ClsOpenShop> response) {
                    if (response.isSuccessful()) {
                        progress.dismiss();
                        ClsOpenShop clsOpenShop = response.body();
                        callToast(clsOpenShop.getMessage());
                        Intent intent = new Intent(OpenShopActivity.this, MainSellerActivity.class);
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
                public void onFailure(Call<ClsOpenShop> call, Throwable t) {
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
            actionBar.setTitle("Open Shop");
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