package com.testing.bestcommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.testing.bestcommerce.adapter.AdapterInbox;
import com.testing.bestcommerce.adapter.AdapterInboxDetail;
import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.model.ClsCheckout;
import com.testing.bestcommerce.model.ClsDelivery;
import com.testing.bestcommerce.model.ClsHistoryPembeli;
import com.testing.bestcommerce.model.ClsInboxCheckout;
import com.testing.bestcommerce.model.ClsInboxDetail;
import com.testing.bestcommerce.model.ClsInboxPengiriman;
import com.testing.bestcommerce.model.ClsKonfirmasiPenerimaan;
import com.testing.bestcommerce.model.ClsKonfirmasiPengiriman;
import com.testing.bestcommerce.model.ClsProduct;
import com.testing.bestcommerce.model.ClsProductDetail;
import com.testing.bestcommerce.orm.tbl_pengiriman;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxDetailActivity extends AppCompatActivity {

    SharedPreferences pref;
    Spinner spinner_kurir;
    LinearLayout linear_alamat,linear_no_data,linear_kurir,lnr_alamat,bottom;
    RelativeLayout progress_master;
    String id_pengiriman, param_kurir, param_alamat;
    String user_type = "";
    public static InboxDetailActivity pInstance;
    EditText edt_alamat,edt_pembeli,edt_kurir;
    List<ClsInboxPengiriman> clsInboxPengiriman;
    List<ClsInboxCheckout> clsInboxCheckouts;
    RecyclerView rv_detail;
    AdapterInboxDetail adapterInboxDetail;
    MaterialRippleLayout mrl_konfirmasi_pengiriman,mrl_konfirmasi_penerimaan;
    TextView txt_nama_kurir,txt_nama_kurir_for_seller,txt_label_kurir;
    ClsHistoryPembeli clsHistoryPembeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_detail);
        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        setupActionBar();
        lnr_alamat = findViewById(R.id.lnr_alamat);
        mrl_konfirmasi_pengiriman = findViewById(R.id.mrl_konfirmasi_pengiriman);
        mrl_konfirmasi_penerimaan = findViewById(R.id.mrl_konfirmasi_penerimaan);
        progress_master = findViewById(R.id.progress_master);
        linear_alamat = findViewById(R.id.linear_alamat);
        linear_no_data = findViewById(R.id.linear_no_data);
        spinner_kurir = findViewById(R.id.spinner_kurir);
        linear_kurir = findViewById(R.id.linear_kurir);
        edt_alamat = findViewById(R.id.edt_alamat);
        rv_detail = findViewById(R.id.rv_detail);
        id_pengiriman = getIntent().getStringExtra("id_pengiriman");
        param_kurir = getIntent().getStringExtra("param_kurir");
        param_alamat = getIntent().getStringExtra("param_alamat");
        txt_nama_kurir = findViewById(R.id.txt_nama_kurir);
        edt_pembeli = findViewById(R.id.edt_pembeli);
        edt_kurir = findViewById(R.id.edt_kurir);
        txt_nama_kurir_for_seller = findViewById(R.id.txt_nama_kurir_for_seller);
        txt_label_kurir = findViewById(R.id.txt_label_kurir);
        bottom = findViewById(R.id.bottom);
        pInstance = this;
        getData();
        edt_alamat.setText(param_alamat);
        if(param_kurir == null){
            txt_label_kurir.setVisibility(View.GONE);
            linear_kurir.setVisibility(View.GONE);
            //txt_nama_kurir_for_seller.setVisibility(View.GONE);
            txt_nama_kurir.setVisibility(View.GONE);
            edt_kurir.setVisibility(View.GONE);
            edt_kurir.setText("");
        } else {
            linear_kurir.setVisibility(View.VISIBLE);
            txt_nama_kurir.setText("Nama Kurir : "+param_kurir);
            edt_kurir.setText(param_kurir);
            edt_kurir.setVisibility(View.VISIBLE);
            //txt_nama_kurir_for_seller.setVisibility(View.VISIBLE);
            txt_label_kurir.setVisibility(View.VISIBLE);
        }

        rv_detail.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_detail.setLayoutManager(layoutManager);

        mrl_konfirmasi_pengiriman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnr_alamat.setVisibility(View.GONE);
                mrl_konfirmasi_pengiriman.setVisibility(View.GONE);
                mrl_konfirmasi_penerimaan.setVisibility(View.GONE);
                linear_alamat.setVisibility(View.GONE);
                spinner_kurir.setVisibility(View.GONE);
                rv_detail.setVisibility(View.GONE);
                String kurir = spinner_kurir.getSelectedItem().toString();
                confirmDelivery(kurir);
            }
        });
        mrl_konfirmasi_penerimaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmAcceptance();
            }
        });
    }

    void confirmAcceptance(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            linear_alamat.setVisibility(View.GONE);
            bottom.setVisibility(View.GONE);
            Call<ClsKonfirmasiPenerimaan> call = GeneralSetting.getRetrofit().confirmAcceptance(Integer.parseInt(id_pengiriman), pref.getInt("user_id", 0));
            call.enqueue(new Callback<ClsKonfirmasiPenerimaan>() {
                @Override
                public void onResponse(Call<ClsKonfirmasiPenerimaan> call, Response<ClsKonfirmasiPenerimaan> response) {
                    if (response.isSuccessful()){
                        ClsKonfirmasiPenerimaan data = response.body();
                        callToast("Confirmation Success");
                        progress_master.setVisibility(View.GONE);

                        Intent intent = new Intent(InboxDetailActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to confirm data");

                        Intent intent = new Intent(InboxDetailActivity.this, InboxActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ClsKonfirmasiPenerimaan> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");

                    Intent intent = new Intent(InboxDetailActivity.this, InboxActivity.class);
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

    void confirmDelivery(String kurir){
        try {
            progress_master.setVisibility(View.VISIBLE);
            Call<ClsKonfirmasiPengiriman> call = GeneralSetting.getRetrofit().confirmDelivery(Integer.parseInt(id_pengiriman), kurir, pref.getInt("user_id", 0));
            call.enqueue(new Callback<ClsKonfirmasiPengiriman>() {
                @Override
                public void onResponse(Call<ClsKonfirmasiPengiriman> call, Response<ClsKonfirmasiPengiriman> response) {
                    if (response.isSuccessful()){
                        ClsKonfirmasiPengiriman data = response.body();
                        callToast(data.getMessage());
                        progress_master.setVisibility(View.GONE);

                        Intent intent = new Intent(InboxDetailActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to confirm data");

                        Intent intent = new Intent(InboxDetailActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ClsKonfirmasiPengiriman> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");

                    Intent intent = new Intent(InboxDetailActivity.this, MainActivity.class);
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

    public void setUserType(String text){
        user_type = text;
    }

    void getData(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            Call<ClsInboxDetail> call = GeneralSetting.getRetrofit().getDeliveryInboxDetail(Integer.parseInt(id_pengiriman));
            call.enqueue(new Callback<ClsInboxDetail>() {
                @Override
                public void onResponse(Call<ClsInboxDetail> call, Response<ClsInboxDetail> response) {
                    if (response.isSuccessful()){
                        ClsInboxDetail list = response.body();
                        //clsInboxPengiriman = response.body().getData_pengiriman();
                        if(list.getData_pengiriman().size() != 0){
                            linear_kurir.setVisibility(View.VISIBLE);
                            spinner_kurir.setVisibility(View.VISIBLE);
                            lnr_alamat.setVisibility(View.VISIBLE);
                            mrl_konfirmasi_pengiriman.setVisibility(View.VISIBLE);
                            mrl_konfirmasi_penerimaan.setVisibility(View.GONE);
                            linear_alamat.setVisibility(View.VISIBLE);
                            edt_alamat.setEnabled(false);

                            edt_kurir.setEnabled(false);
                            txt_nama_kurir_for_seller.setVisibility(View.GONE);
                            txt_nama_kurir.setVisibility(View.GONE);
                        } else {
                            edt_alamat.setEnabled(false);
                            linear_kurir.setVisibility(View.VISIBLE);
                            spinner_kurir.setVisibility(View.GONE);
                            lnr_alamat.setVisibility(View.VISIBLE);
                            spinner_kurir.setEnabled(false);
                            mrl_konfirmasi_pengiriman.setVisibility(View.GONE);
                            mrl_konfirmasi_penerimaan.setVisibility(View.VISIBLE);

                            edt_kurir.setEnabled(false);
                            txt_nama_kurir_for_seller.setVisibility(View.GONE);
                            txt_nama_kurir.setVisibility(View.GONE);
                        }

                        if(list.getData_checkout().size() != 0){
                            clsInboxCheckouts = response.body().getData_checkout();
                            adapterInboxDetail = new AdapterInboxDetail( clsInboxCheckouts, InboxDetailActivity.this);
                            rv_detail.setAdapter(adapterInboxDetail);
                            adapterInboxDetail.notifyDataSetChanged();

                            lnr_alamat.setVisibility(View.VISIBLE);
                            linear_alamat.setVisibility(View.VISIBLE);
                            rv_detail.setVisibility(View.VISIBLE);
                            progress_master.setVisibility(View.GONE);
                            edt_alamat.setEnabled(false);
                            edt_kurir.setEnabled(false);

                            //Check pembeli---------------------------------------------------------
                            clsHistoryPembeli = response.body().getNama_pembeli();
                            edt_pembeli.setText(clsHistoryPembeli.getFirst_name()+" "+clsHistoryPembeli.getLast_name());
                            edt_pembeli.setEnabled(false);
                        } else {

                        }
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to get data");
                    }
                }

                @Override
                public void onFailure(Call<ClsInboxDetail> call, Throwable t) {
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
            actionBar.setTitle("Inbox Detail");
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