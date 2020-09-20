package com.testing.bestcommerce;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.testing.bestcommerce.api.GeneralSetting;
import com.testing.bestcommerce.api.MobileService;
import com.testing.bestcommerce.model.ClsLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextInputEditText edt_username, edt_password;
    Button btn_sign_in;
    ProgressDialog progress;
    TextView sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getApplicationContext().getSharedPreferences("ecommerce", 0);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username =  edt_username.getText().toString().trim();
                String password =  edt_password.getText().toString().trim();
                if (username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    login();
                }
            }
        });
        if (this.checkSession()) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        sign_up = findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private void login(){
        try {
            progress = new ProgressDialog(LoginActivity .this);
            progress.setMessage("Please Wait...");
            progress.setTitle("Connecting to server");
            progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
            progress.show();
            ProgressBar progressbar= progress.findViewById(android.R.id.progress);
            progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#0065B3"), android.graphics.PorterDuff.Mode.SRC_IN);
            MobileService service = GeneralSetting.getRetrofit();
            Call<ClsLogin> call=  service.getToken(edt_username.getText().toString().trim(), edt_password.getText().toString().trim());
            call.enqueue(new Callback<ClsLogin>() {
                @Override
                public void onResponse(Call<ClsLogin> call, Response<ClsLogin> response) {
                    ClsLogin clsResponseLogin = response.body();
                    if (response.isSuccessful()){
                        editor = pref.edit();
                        editor.putString("token", clsResponseLogin.getToken());
                        //editor.putString("message", clsResponseLogin.getMessage());
                        editor.putInt("user_id", clsResponseLogin.getUser_detail().get(0).getId());
                        editor.putString("first_name", clsResponseLogin.getUser_detail().get(0).getFirst_name());
                        editor.putString("last_name", clsResponseLogin.getUser_detail().get(0).getLast_name());
                        editor.putString("email", clsResponseLogin.getUser_detail().get(0).getEmail());
                        editor.putString("username", clsResponseLogin.getUser_detail().get(0).getUsername());
                        editor.putString("phone", clsResponseLogin.getUser_detail().get(0).getPhone());
                        editor.putString("address", clsResponseLogin.getUser_detail().get(0).getAddress());
                        editor.apply();
                        callToast(clsResponseLogin.getMessage());
                        progress.dismiss();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        startActivity(intent);
                    } else {
                        callToast("Login failed, please check username or password");
                        progress.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ClsLogin> call, Throwable t) {
                    callToast("Failed connecting to server");
                    progress.dismiss();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    boolean checkSession(){
        if(!pref.getString("username","").equals("")){
            return true;
        }
        return false;
    }

    void callToast(String sMessage){
        Toast.makeText(getApplicationContext(),sMessage,Toast.LENGTH_SHORT).show();
    }
}