package com.example.adminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    MaterialCardView general;
    MaterialCardView o2;
    MaterialCardView icu;
    MaterialCardView ventilator;
    TextInputLayout HName;
    String vHName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HName=findViewById(R.id.HName);
        Intent i=getIntent();
        vHName=i.getStringExtra("HName");
        general=(MaterialCardView)findViewById(R.id.general);
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,update_beds.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);
            }
        });
        o2=(MaterialCardView)findViewById(R.id.oxygen);
        o2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,update_o2beds.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);
            }
        });
        icu=(MaterialCardView)findViewById(R.id.icu);
        icu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,update_icu_beds.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);
            }
        });
        ventilator=(MaterialCardView)findViewById(R.id.ventilator);
        ventilator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ventilator_beds.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);
            }
        });
    }
}