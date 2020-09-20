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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testing.bestcommerce.adapter.AdapterHistoryDetail;
import com.testing.bestcommerce.adapter.AdapterInboxDetail;
import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.model.ClsHistoryCheckout;
import com.testing.bestcommerce.model.ClsHistoryOrder;
import com.testing.bestcommerce.model.ClsHistoryPembeli;
import com.testing.bestcommerce.model.ClsInboxCheckout;
import com.testing.bestcommerce.model.ClsInboxDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailHistoryActivity extends AppCompatActivity {

    SharedPreferences pref;
    public static DetailHistoryActivity pInstance;
    String id_pengiriman, param_kurir,param_alamat;
    RelativeLayout progress_master;
    LinearLayout linear_no_data,linear_kurir,lnr_alamat,linear_alamat;
    EditText edt_alamat, edt_kurir,edt_pembeli;
    RecyclerView rv_detail;
    List<ClsHistoryCheckout> clsHistoryCheckouts;
    ClsHistoryPembeli clsHistoryPembeli;
    AdapterHistoryDetail adapterHistoryDetail;
    TextView txt_nama_kurir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_history);
        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        setupActionBar();
        pInstance = this;
        id_pengiriman = getIntent().getStringExtra("id_pengiriman");
        param_kurir = getIntent().getStringExtra("param_kurir");
        param_alamat = getIntent().getStringExtra("param_alamat");
        linear_no_data = findViewById(R.id.linear_no_data);
        linear_kurir = findViewById(R.id.linear_kurir);
        lnr_alamat = findViewById(R.id.lnr_alamat);
        edt_alamat = findViewById(R.id.edt_alamat);
        rv_detail = findViewById(R.id.rv_detail);
        progress_master = findViewById(R.id.progress_master);
        txt_nama_kurir = findViewById(R.id.txt_nama_kurir);
        linear_alamat = findViewById(R.id.linear_alamat);
        edt_kurir = findViewById(R.id.edt_kurir);
        edt_pembeli = findViewById(R.id.edt_pembeli);
        getData();

        edt_alamat.setText(param_alamat);
        if(param_kurir == null){
            txt_nama_kurir.setVisibility(View.GONE);
            edt_kurir.setText("");
        } else {
            txt_nama_kurir.setText("Nama Kurir : "+param_kurir);
            edt_kurir.setText(param_kurir);
        }

        rv_detail.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rv_detail.setLayoutManager(layoutManager);
    }

    void getData(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            Call<ClsHistoryOrder> call = GeneralSetting.getRetrofit().getHistoryOrder(Integer.parseInt(id_pengiriman));
            call.enqueue(new Callback<ClsHistoryOrder>() {
                @Override
                public void onResponse(Call<ClsHistoryOrder> call, Response<ClsHistoryOrder> response) {
                    if (response.isSuccessful()){
                        ClsHistoryOrder list = response.body();
                        if(list.getData_checkout().size() != 0){
                            clsHistoryCheckouts = response.body().getData_checkout();
                            adapterHistoryDetail = new AdapterHistoryDetail( clsHistoryCheckouts, DetailHistoryActivity.this);
                            rv_detail.setAdapter(adapterHistoryDetail);
                            adapterHistoryDetail.notifyDataSetChanged();

                            //linear_kurir.setVisibility(View.VISIBLE);
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
                        }
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to get data");

                        Intent intent = new Intent(DetailHistoryActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }


                }

                @Override
                public void onFailure(Call<ClsHistoryOrder> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");

                    Intent intent = new Intent(DetailHistoryActivity.this, MainActivity.class);
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

    public void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("Detail History");
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