package com.testing.bestcommerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orm.SugarContext;
import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.model.ClsProduct;
import com.testing.bestcommerce.orm.tbl_new_cart;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    SharedPreferences pref;
    String id_produk;
    //private View parent_view;
    FloatingActionButton fab_add;
    ImageView iv_product;
    TextView txt_id_produk, txt_id_penjual, txt_nama_produk, txt_nama_toko, txt_harga, txt_deskripsi;
    CoordinatorLayout parent_view;
    RelativeLayout progress_master;
    LinearLayout linear_no_data;
    int cart_id_produk,cart_id_penjual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        linear_no_data = findViewById(R.id.linear_no_data);
        progress_master = findViewById(R.id.progress_master);
        parent_view = findViewById(R.id.parent_view);
        iv_product = findViewById(R.id.iv_product);
        txt_id_produk = findViewById(R.id.txt_id_produk);
        txt_id_penjual = findViewById(R.id.txt_id_penjual);
        txt_nama_produk = findViewById(R.id.txt_nama_produk);
        txt_nama_toko = findViewById(R.id.txt_nama_toko);
        txt_harga = findViewById(R.id.txt_harga);
        txt_deskripsi = findViewById(R.id.txt_deskripsi);
        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        SugarContext.init(this);
        id_produk = getIntent().getStringExtra("id_produk");
        fab_add = findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tbl_new_cart iCart = new tbl_new_cart();
                    iCart.id_produk = cart_id_produk;
                    iCart.id_penjual = cart_id_penjual;
                    iCart.id_user = pref.getInt("user_id", 0);
                    iCart.nama_produk = txt_nama_produk.getText().toString().trim();
                    iCart.img = "Tes.jpg";
                    iCart.harga = txt_harga.getText().toString().trim();
                    iCart.qty = 1;
                    iCart.sub_total = 0;
                    iCart.total = Integer.parseInt(txt_harga.getText().toString().trim()) * 1;
                    iCart.created_by = String.valueOf(pref.getInt("user_id", 0));
                    iCart.created_date = "2020-08-19";
                    iCart.modified_by = String.valueOf(pref.getInt("user_id", 0));
                    iCart.modified_date = "2020-08-19";
                    iCart.save();

                    Intent intent = new Intent(DetailProductActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    finish();
                    startActivity(intent);
                    Toast.makeText(DetailProductActivity.this, "Item berhasil masuk ke keranjang", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        //setupActionBar();
        getDetail();
        initToolbar();
    }

    void getDetail(){
        try {
            progress_master.setVisibility(View.VISIBLE);
            Call<ClsProduct> call = GeneralSetting.getRetrofit().getProductByID(Integer.parseInt(id_produk));
            call.enqueue(new Callback<ClsProduct>() {
                @Override
                public void onResponse(Call<ClsProduct> call, Response<ClsProduct> response) {
                    if (response.isSuccessful()){
                        ClsProduct list = response.body();
                        if(list.getData().size() != 0){
                            cart_id_produk = list.getData().get(0).getId();
                            cart_id_penjual = list.getData().get(0).getId_penjual();
                            txt_id_produk.setText(Integer.toString(list.getData().get(0).getId()));
                            txt_id_penjual.setText(Integer.toString(list.getData().get(0).getId_penjual()));
                            txt_nama_produk.setText(list.getData().get(0).getNama_produk());
                            txt_nama_toko.setText(list.getData().get(0).getNama_toko());

                            //Locale localeID = new Locale("in", "ID");
                            //NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                            //txt_harga.setText(formatRupiah.format(list.getData().get(0).getHarga()));
                            //txt_harga.setText("Rp "+list.getData().get(0).getHarga());
                            txt_harga.setText(list.getData().get(0).getHarga());

                            txt_deskripsi.setText(list.getData().get(0).getDeskripsi_produk());
                            progress_master.setVisibility(View.GONE);
                            parent_view.setVisibility(View.VISIBLE);
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

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail");
        Tools.setSystemBarColor(this);
    }

    void callToast(String sMessage){
        Toast.makeText(getApplicationContext(),sMessage,Toast.LENGTH_SHORT).show();
    }

//    private void initComponent() {
//        // nested scrollview
//        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);
//
//        // section description
//        bt_toggle_description = (ImageButton) findViewById(R.id.bt_toggle_description);
//        lyt_expand_description = (View) findViewById(R.id.lyt_expand_description);
//        bt_toggle_description.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggleSection(view, lyt_expand_description);
//            }
//        });
//
//        // expand first description
//        toggleArrow(bt_toggle_description);
//        lyt_expand_description.setVisibility(View.VISIBLE);
//
//        ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(parent_view, "Add to Cart", Snackbar.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void toggleSection(View bt, final View lyt) {
//        boolean show = toggleArrow(bt);
//        if (show) {
//            com.material.components.utils.ViewAnimation.expand(lyt, new com.material.components.utils.ViewAnimation.AnimListener() {
//                @Override
//                public void onFinish() {
//                    Tools.nestedScrollTo(nested_scroll_view, lyt);
//                }
//            });
//        } else {
//            com.material.components.utils.ViewAnimation.collapse(lyt);
//        }
//    }
//
//    public boolean toggleArrow(View view) {
//        if (view.getRotation() == 0) {
//            view.animate().setDuration(200).rotation(180);
//            return true;
//        } else {
//            view.animate().setDuration(200).rotation(0);
//            return false;
//        }
//    }

//    public void setupActionBar() {
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setTitle("Detail Product");
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}