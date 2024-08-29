package com.example.hbms;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FirstScreen extends AppCompatActivity {
    Button user;
    Button Hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        user=(Button)findViewById(R.id.user);
        Hospital=(Button)findViewById(R.id.hospital);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstScreen.this,Login.class);
                startActivity(intent);
            }
        });
        Hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstScreen.this,Login1.class);
                startActivity(intent);
            }
        });
    }
}