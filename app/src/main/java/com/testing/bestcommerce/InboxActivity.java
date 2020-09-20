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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testing.bestcommerce.adapter.AdapterInbox;
import com.testing.bestcommerce.adapter.AdapterProduct;
import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.model.ClsDeliveryDetailForSeller;
import com.testing.bestcommerce.model.ClsDeliveryForSeller;
import com.testing.bestcommerce.model.ClsProduct;
import com.testing.bestcommerce.model.ClsProductDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxActivity extends AppCompatActivity {

    SharedPreferences pref;
    RelativeLayout progress_master;
    RecyclerView recyclerView;
    LinearLayout linear_no_data;
    List<ClsDeliveryDetailForSeller> clsDeliveryDetailForSellers;
    AdapterInbox adapterInbox;
    TextView txt_user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        setupActionBar();
        progress_master = findViewById(R.id.progress_master);
        recyclerView = findViewById(R.id.recyclerView);
        linear_no_data = findViewById(R.id.linear_no_data);
        txt_user_type = findViewById(R.id.txt_user_type);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getData();
    }

    void getData(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            Call<ClsDeliveryForSeller> call = GeneralSetting.getRetrofit().getDeliveryForSeller(pref.getInt("user_id", 0));
            call.enqueue(new Callback<ClsDeliveryForSeller>() {
                @Override
                public void onResponse(Call<ClsDeliveryForSeller> call, Response<ClsDeliveryForSeller> response) {
                    if (response.isSuccessful()){
                        ClsDeliveryForSeller list = response.body();
                        //txt_user_type.setText(list.getUser());
                        if(list.getData().size() != 0){
                            clsDeliveryDetailForSellers = response.body().getData();
                            adapterInbox = new AdapterInbox( clsDeliveryDetailForSellers, InboxActivity.this);
                            recyclerView.setAdapter(adapterInbox);
                            adapterInbox.notifyDataSetChanged();

                            recyclerView.setVisibility(View.VISIBLE);
                            progress_master.setVisibility(View.GONE);
                        } else {
                            progress_master.setVisibility(View.GONE);
                            linear_no_data.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to get data");

                        Intent intent = new Intent(InboxActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ClsDeliveryForSeller> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");

                    Intent intent = new Intent(InboxActivity.this, MainActivity.class);
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
            actionBar.setTitle("Inbox");
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