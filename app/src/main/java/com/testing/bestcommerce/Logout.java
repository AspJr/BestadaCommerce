package com.testing.bestcommerce;

import android.content.Context;
import android.content.SharedPreferences;

public class Logout {
    SharedPreferences pref;
    public Logout(Context contexts){
        pref = contexts.getApplicationContext().getSharedPreferences("ecommerce", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.remove("token");
        edit.remove("user_id");
        edit.remove("first_name");
        edit.remove("last_name");
        edit.remove("email");
        edit.remove("username");
        edit.remove("phone");
        edit.remove("address");
        edit.apply();
    }
}