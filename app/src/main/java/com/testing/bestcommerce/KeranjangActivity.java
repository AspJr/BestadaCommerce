package com.testing.bestcommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;
import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.testing.bestcommerce.adapter.AdapterCart;
import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.model.ClsCheckout;
import com.testing.bestcommerce.model.ClsDelivery;
import com.testing.bestcommerce.model.ClsProduct;
import com.testing.bestcommerce.orm.tbl_new_cart;
import com.testing.bestcommerce.orm.tbl_pengiriman;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangActivity extends AppCompatActivity {
    SharedPreferences pref;
    RecyclerView rv_cart;
    LinearLayout linear_no_data,bottom,linear_alamat;
    List<tbl_new_cart> p_tbl_new_carts;
    AdapterCart adapterCart;
    public static KeranjangActivity pInstance;
    MaterialRippleLayout mrl_checkout;
    TextView txt_total;
    RelativeLayout progress_master;
    EditText edt_alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        SugarContext.init(this);
        setupActionBar();

        edt_alamat = findViewById(R.id.edt_alamat);
        linear_alamat = findViewById(R.id.linear_alamat);
        progress_master = findViewById(R.id.progress_master);
        rv_cart = findViewById(R.id.rv_cart);
        linear_no_data = findViewById(R.id.linear_no_data);
        bottom = findViewById(R.id.bottom);
        txt_total = findViewById(R.id.txt_total);
        mrl_checkout = findViewById(R.id.mrl_checkout);
        mrl_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String alamat = edt_alamat.getText().toString().trim();
                if(alamat.equals("")){
                    callToast("Please fill the blank");
                } else {
                    checkOut();
                }
            }
        });
        pInstance = this;
        setData();

        rv_cart.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_cart.setLayoutManager(layoutManager);
    }

    void checkOut(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            linear_no_data.setVisibility(View.GONE);
            //rv_cart.setVisibility(View.GONE);
            bottom.setVisibility(View.GONE);
            //progress_master.setVisibility(View.GONE);
            linear_alamat.setVisibility(View.GONE);

            List<tbl_new_cart> iCart = Select.from(tbl_new_cart.class)
                    //.where(Condition.prop("STATUS").eq("0"))
                    .list();
            Call<ClsCheckout> call = GeneralSetting.getRetrofit().checkOut(iCart);
            call.enqueue(new Callback<ClsCheckout>() {
                @Override
                public void onResponse(Call<ClsCheckout> call, Response<ClsCheckout> response) {
                    if(response.isSuccessful()){
                        ClsCheckout data = response.body();
                        if(data.getStatus() == 0){
                            //progress_master.setVisibility(View.GONE);
                            callToast(data.getMessage());
                            tbl_new_cart.deleteAll(tbl_new_cart.class);
                            List<tbl_pengiriman> responseCheck = response.body().getData();
                            for (tbl_pengiriman check : responseCheck){
                                tbl_pengiriman iTbl = new tbl_pengiriman(
                                        check.getId_checkout(),
                                        check.getId_produk(),
                                        check.getId_penjual(),
                                        check.getId_user(),
                                        check.getNama_produk(),
                                        check.getImage(),
                                        check.getHarga(),
                                        check.getQty(),
                                        check.getSub_total(),
                                        check.getTotal(),
                                        edt_alamat.getText().toString().trim(),
                                        "",
                                        check.getStatus_checkout(),
                                        check.getCreated_by(),
                                        check.getCreated_date(),
                                        check.getModified_by(),
                                        check.getModified_date());
                                iTbl.save();
                            }
                            delivery();
                        }
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to checkout data");

                        Intent intent = new Intent(KeranjangActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ClsCheckout> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");

                    Intent intent = new Intent(KeranjangActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    startActivity(intent);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void delivery(){
        try {
            List<tbl_pengiriman> iPengiriman = Select.from(tbl_pengiriman.class)
                    .list();
            Gson gson = new Gson();
            String json = gson.toJson(iPengiriman);
            String val = json;
            Log.d("TAG", "onOptionsItemSelected: "+json);
            Call<ClsDelivery> call = GeneralSetting.getRetrofit().delivery(iPengiriman);
            call.enqueue(new Callback<ClsDelivery>() {
                @Override
                public void onResponse(Call<ClsDelivery> call, Response<ClsDelivery> response) {
                    if (response.isSuccessful()){
                        ClsDelivery data = response.body();
                        if(data.getStatus() == 0){
                            callToast(data.getMessage());
                            progress_master.setVisibility(View.GONE);

                            tbl_pengiriman.deleteAll(tbl_pengiriman.class);
                            Intent intent = new Intent(KeranjangActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(intent);
                        }
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to checkout data");

                        Intent intent = new Intent(KeranjangActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ClsDelivery> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");

                    Intent intent = new Intent(KeranjangActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    startActivity(intent);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setTotal(float total){
        txt_total.setText(String.valueOf(total));
    }

    void setData(){
        int check = (int) tbl_new_cart.count(tbl_new_cart.class);
        if(check > 0){
            p_tbl_new_carts = tbl_new_cart.findWithQuery(tbl_new_cart.class, "SELECT * FROM TBLNEWCART ORDER BY MODIFIEDDATE DESC");
            adapterCart = new AdapterCart(p_tbl_new_carts, pInstance);
            rv_cart.setAdapter(adapterCart);
            linear_no_data.setVisibility(View.GONE);
            rv_cart.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            progress_master.setVisibility(View.GONE);
            linear_alamat.setVisibility(View.VISIBLE);
        } else {
            linear_no_data.setVisibility(View.VISIBLE);
            rv_cart.setVisibility(View.GONE);
            bottom.setVisibility(View.GONE);
            progress_master.setVisibility(View.GONE);
            linear_alamat.setVisibility(View.GONE);
        }
    }

    public void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Cart");
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