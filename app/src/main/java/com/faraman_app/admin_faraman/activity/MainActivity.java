package com.faraman_app.admin_faraman.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.faraman_app.admin_faraman.R;
import com.faraman_app.admin_faraman.interfaces.Initialize;

public class MainActivity extends AppCompatActivity implements Initialize {
    private Button buttonAddLawyer, buttonShowLawyers, buttonAddTypeOfLawyers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        buttonAddLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentTo(MainActivity.this, AddAdviserActivity.class);
            }
        });
        buttonAddTypeOfLawyers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentTo(MainActivity.this, TypeOfLawyersActivity.class);

            }
        });
        buttonShowLawyers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentTo(MainActivity.this, ShowLawyersActivity.class);

            }
        });

    }

    @Override
    public void init() {
        buttonAddLawyer = findViewById(R.id.buttonAddLawyer);
        buttonShowLawyers = findViewById(R.id.buttonShowLawyers);
        buttonAddTypeOfLawyers = findViewById(R.id.buttonAddTypeOfLawyers);

    }

    @Override
    public void intentTo(Context context, Class toClass) {
        Intent intent = new Intent(context, toClass);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
        
    }

}
