package com.testing.bestcommerce;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.orm.SugarContext;
import com.testing.bestcommerce.adapter.AdapterAllProducts;
import com.testing.bestcommerce.adapter.AdapterProduct;
import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.model.ClsProduct;
import com.testing.bestcommerce.model.ClsProductDetail;
import com.testing.bestcommerce.orm.tbl_new_cart;
import com.testing.bestcommerce.orm.tbl_pengiriman;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;
    ImageButton img_btn_inbox, img_btn_history;
    FloatingActionButton fab_open_shop;
    List<ClsProductDetail> clsProductDetails;
    AdapterAllProducts adapterAllProducts;
    RelativeLayout progress_master;
    ScrollView scroll_main;
    private RecyclerView recyclerView;
    LinearLayout linear_no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        SugarContext.init(this);
        linear_no_data = findViewById(R.id.linear_no_data);
        progress_master = findViewById(R.id.progress_master);
        scroll_main = findViewById(R.id.scroll_main);
        recyclerView = findViewById(R.id.recyclerView);
        img_btn_inbox = findViewById(R.id.img_btn_inbox);
        img_btn_inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, InboxActivity.class);
                startActivity(i);
            }
        });
        img_btn_history = findViewById(R.id.img_btn_history);
        img_btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(i);
            }
        });
        fab_open_shop = findViewById(R.id.fab_open_shop);
        fab_open_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, OpenShopActivity.class);
                startActivity(i);
            }
        });
        setupActionBar();
        getAllProducts();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, 8, true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        //recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);
    }

    void getAllProducts(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            Call<ClsProduct> call = GeneralSetting.getRetrofit().getAllProducts();
            call.enqueue(new Callback<ClsProduct>() {
                @Override
                public void onResponse(Call<ClsProduct> call, Response<ClsProduct> response) {
                    if (response.isSuccessful()){
                        ClsProduct list = response.body();
                        if(list.getData().size() != 0){
                            clsProductDetails = response.body().getData();
                            adapterAllProducts = new AdapterAllProducts( clsProductDetails, MainActivity.this);
                            recyclerView.setAdapter(adapterAllProducts);
                            adapterAllProducts.notifyDataSetChanged();

                            recyclerView.setVisibility(View.VISIBLE);
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
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("BestadaCommerce");
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        int id = item.getItemId();

        if (id==R.id.keranjang){
            Intent i = new Intent(MainActivity.this, KeranjangActivity.class);
            startActivity(i);
        }

        if (id==R.id.profile){
            Intent i = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(i);
        }

        if (id==R.id.delete_sugar){
            tbl_new_cart.deleteAll(tbl_new_cart.class);
            tbl_pengiriman.deleteAll(tbl_pengiriman.class);
        }
        if (id==R.id.logout){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder.setTitle("Are you sure to logout?");
            alertDialogBuilder
                    .setMessage(" ")
                    .setIcon(R.drawable.ic_baseline_error_outline)
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            new Logout(MainActivity.this);
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    void callToast(String sMessage){
        Toast.makeText(getApplicationContext(),sMessage,Toast.LENGTH_SHORT).show();
    }
}