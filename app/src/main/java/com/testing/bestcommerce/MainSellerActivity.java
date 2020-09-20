package com.testing.bestcommerce;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testing.bestcommerce.adapter.AdapterProduct;
import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.model.ClsProduct;
import com.testing.bestcommerce.model.ClsProductDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainSellerActivity extends AppCompatActivity {
    SharedPreferences pref;
    ProgressDialog progress;
    RelativeLayout progress_master;
    RecyclerView rv_product;
    AdapterProduct adapterProduct;
    List<ClsProductDetail> clsProductDetails;
    LinearLayout linear_no_data;
    RelativeLayout relativeNoConnection, relativeHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seller);
        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        relativeHeader = findViewById(R.id.relativeHeader);
        progress_master = findViewById(R.id.progress_master);
        rv_product = findViewById(R.id.rv_product);
        linear_no_data = findViewById(R.id.linear_no_data);
        setupActionBar();
        getData();

        rv_product.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_product.setLayoutManager(layoutManager);
    }

    void getData(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            //rv_product.setVisibility(View.GONE);
            Call<ClsProduct> call = GeneralSetting.getRetrofit().getProduct(pref.getInt("user_id", 0));
            call.enqueue(new Callback<ClsProduct>() {
                @Override
                public void onResponse(Call<ClsProduct> call, Response<ClsProduct> response) {
                    if(response.isSuccessful()){
                        ClsProduct list = response.body();
                        if(list.getData().size() != 0){
                            clsProductDetails = response.body().getData();
                            adapterProduct = new AdapterProduct( clsProductDetails, MainSellerActivity.this);
                            rv_product.setAdapter(adapterProduct);
                            adapterProduct.notifyDataSetChanged();

                            rv_product.setVisibility(View.VISIBLE);
                            progress_master.setVisibility(View.GONE);
                        } else {
                            progress_master.setVisibility(View.GONE);
                            linear_no_data.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to get data");
                    }
                }

                @Override
                public void onFailure(Call<ClsProduct> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");
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
            actionBar.setTitle("Halaman Penjual");
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_seller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                //onBackPressed();
                Intent intent = new Intent(MainSellerActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
                return true;
            case R.id.add_product:
                Intent i = new Intent(MainSellerActivity.this, UploadProductActivity.class);
                startActivity(i);
                return true;
        }
//        int id = item.getItemId();
//        if (id==R.id.add_product){
//            Intent i = new Intent(MainSellerActivity.this, UploadProductActivity.class);
//            startActivity(i);
//        }
        return super.onOptionsItemSelected(item);
    }

    void callToast(String sMessage){
        Toast.makeText(getApplicationContext(),sMessage,Toast.LENGTH_SHORT).show();
    }
}