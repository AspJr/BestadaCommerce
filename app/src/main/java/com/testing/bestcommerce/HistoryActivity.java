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
import android.widget.Toast;

import com.testing.bestcommerce.adapter.AdapterHistory;
import com.testing.bestcommerce.adapter.AdapterInbox;
import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.model.ClsDeliveryDetailForSeller;
import com.testing.bestcommerce.model.ClsDeliveryForSeller;
import com.testing.bestcommerce.model.ClsDetailHistory;
import com.testing.bestcommerce.model.ClsHistory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    SharedPreferences pref;
    RecyclerView recyclerView;
    LinearLayout linear_no_data;
    RelativeLayout progress_master;
    List<ClsDetailHistory> clsDetailHistories;
    AdapterHistory adapterHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        progress_master = findViewById(R.id.progress_master);
        linear_no_data = findViewById(R.id.linear_no_data);
        recyclerView = findViewById(R.id.recyclerView);
        setupActionBar();
        getData();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    void getData(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            Call<ClsHistory> call = GeneralSetting.getRetrofit().getHistory(pref.getInt("user_id", 0));
            call.enqueue(new Callback<ClsHistory>() {
                @Override
                public void onResponse(Call<ClsHistory> call, Response<ClsHistory> response) {
                    if(response.isSuccessful()){
                        ClsHistory list = response.body();
                        if(list.getData().size() != 0){
                            clsDetailHistories = response.body().getData();
                            adapterHistory = new AdapterHistory( clsDetailHistories, HistoryActivity.this);
                            recyclerView.setAdapter(adapterHistory);
                            adapterHistory.notifyDataSetChanged();

                            recyclerView.setVisibility(View.VISIBLE);
                            progress_master.setVisibility(View.GONE);
                        } else {
                            progress_master.setVisibility(View.GONE);
                            linear_no_data.setVisibility(View.VISIBLE);
                        }
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to get data");

                        Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ClsHistory> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");

                    Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
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
            actionBar.setTitle("History");
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