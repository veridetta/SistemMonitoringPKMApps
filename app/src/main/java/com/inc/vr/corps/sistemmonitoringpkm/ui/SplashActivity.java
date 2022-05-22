package com.inc.vr.corps.sistemmonitoringpkm.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.inc.vr.corps.sistemmonitoringpkm.R;
import com.inc.vr.corps.sistemmonitoringpkm.ui.login.LoginActivity;
import com.inc.vr.corps.sistemmonitoringpkm.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        actionDelay();
    }

    void checkIslogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        boolean islogin = sharedPreferences.getBoolean("isLogin", false);
        if(islogin){
            startActivity(new Intent(this, MainActivity.class));
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    void actionDelay(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkIslogin();
            }
        },1500);
    }
}