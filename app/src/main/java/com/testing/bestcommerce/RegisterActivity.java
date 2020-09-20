package com.testing.bestcommerce;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.model.ClsProduct;
import com.testing.bestcommerce.model.ClsRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText edt_firstname,edt_lastname,edt_username,edt_password,edt_email,edt_phone;
    Button btn_register;
    LinearLayout linear_register;
    RelativeLayout progress_master;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupActionBar();

        linear_register = findViewById(R.id.linear_register);
        progress_master = findViewById(R.id.progress_master);
        edt_firstname = findViewById(R.id.edt_firstname);
        edt_lastname = findViewById(R.id.edt_lastname);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_email = findViewById(R.id.edt_email);
        edt_phone = findViewById(R.id.edt_phone);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    void register(){
        try {
            linear_register.setVisibility(View.GONE);
            progress_master.setVisibility(View.VISIBLE);
            Call<ClsRegister> call = GeneralSetting.getRetrofit().register(edt_firstname.getText().toString().trim(),
                    edt_lastname.getText().toString().trim(),edt_email.getText().toString().trim(),
                    edt_username.getText().toString().trim(),edt_password.getText().toString().trim(),
                    edt_phone.getText().toString().trim());
            call.enqueue(new Callback<ClsRegister>() {
                @Override
                public void onResponse(Call<ClsRegister> call, Response<ClsRegister> response) {
                    if (response.isSuccessful()){
                        ClsRegister data = response.body();
                        if (data.getStatus() == 0){
                            callToast(data.getMessage());
                            linear_register.setVisibility(View.GONE);
                            progress_master.setVisibility(View.GONE);

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(intent);
                        } else {
                            callToast(data.getMessage());
                            linear_register.setVisibility(View.VISIBLE);
                            progress_master.setVisibility(View.GONE);
                        }
                    } else {
                        progress_master.setVisibility(View.GONE);
                        callToast("Failed to store data");

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<ClsRegister> call, Throwable t) {
                    progress_master.setVisibility(View.GONE);
                    callToast("Failed to connect to server");

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
            actionBar.setTitle("Register");
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