package com.faraman_app.admin_faraman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.faraman_app.admin_faraman.R;


import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler=new Handler();

        Runnable r=new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();


            }
        };

        handler.postDelayed(r,2000);

    }
}
