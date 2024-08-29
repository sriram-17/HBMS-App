package com.example.adminapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
public class register1 extends AppCompatActivity {
    TextInputLayout t1,o1,v1,t2,o2,v2,t3,o3,v3,t4,o4,v4;
    Button next2;
    TextInputLayout HName,password,email,phone;
    String vHName,vpass,vemail,vphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        next2=(Button)findViewById(R.id.next2);
        t1=(TextInputLayout)findViewById(R.id.totalGeneral);
        t2=(TextInputLayout)findViewById(R.id.totalICU);
        t3=(TextInputLayout)findViewById(R.id.totalO2);
        t4=(TextInputLayout)findViewById(R.id.totalVentilator);
        o1=(TextInputLayout)findViewById(R.id.OccupiedGeneral);
        o2=(TextInputLayout)findViewById(R.id.OccupiedICU);
        o3=(TextInputLayout)findViewById(R.id.OccupiedO2);
        o4=(TextInputLayout)findViewById(R.id.OccupiedVentilator);
        v1=(TextInputLayout)findViewById(R.id.VaccantGeneral);
        v2=(TextInputLayout)findViewById(R.id.VaccantICU);
        v3=(TextInputLayout)findViewById(R.id.VaccantO2);
        v4=(TextInputLayout)findViewById(R.id.VaccantVentilator);
        HName=findViewById(R.id.HName);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        Intent intent=getIntent();
        vHName=intent.getStringExtra("HName");
        vpass=intent.getStringExtra("pass");
       vemail=intent.getStringExtra("email");
        vphone=intent.getStringExtra("phone");
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vt1=t1.getEditText().getText().toString().trim();
                String vt2=t2.getEditText().getText().toString().trim();
                String vt3=t3.getEditText().getText().toString().trim();
                String vt4=t4.getEditText().getText().toString().trim();
                String vo1=o1.getEditText().getText().toString().trim();
                String vo2=o2.getEditText().getText().toString().trim();
                String vo3=o3.getEditText().getText().toString().trim();
                String vo4=o4.getEditText().getText().toString().trim();
                String vv1=v1.getEditText().getText().toString().trim();
                String vv2=v2.getEditText().getText().toString().trim();
                String vv3=v3.getEditText().getText().toString().trim();
                String vv4=v4.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(vt1)){
                    t1.setError("Required");
                    return;
                }else{
                    t1.setError(null);
                }
                if(TextUtils.isEmpty(vt2)){
                    t2.setError("Required");
                    return;
                }else{
                    t2.setError(null);
                }
                if(TextUtils.isEmpty(vt3)){
                    t3.setError("Required");
                    return;
                }else{
                    t3.setError(null);
                }
                if(TextUtils.isEmpty(vt4)){
                    t4.setError("Required");
                    return;
                }else{
                    t4.setError(null);
                }
                if(TextUtils.isEmpty(vo1)){
                    o1.setError("Required");
                    return;
                }else{
                    o1.setError(null);
                }
                if(TextUtils.isEmpty(vo2)){
                    o2.setError("Required");
                    return;
                }else{
                    o2.setError(null);
                }
                if(TextUtils.isEmpty(vo3)){
                    o3.setError("Required");
                    return;
                }else{
                    o3.setError(null);
                }
                if(TextUtils.isEmpty(vo4)){
                    o4.setError("Required");
                    return;
                }else{
                    o4.setError(null);
                }
                if(TextUtils.isEmpty(vv1)){
                    v1.setError("Required");
                    return;
                }else{
                    v1.setError(null);
                }
                if(TextUtils.isEmpty(vv2)){
                    v2.setError("Required");
                    return;
                }else{
                    v2.setError(null);
                }
                if(TextUtils.isEmpty(vv3)){
                    v3.setError("Required");
                    return;
                }else{
                    v3.setError(null);
                }
                if(TextUtils.isEmpty(vv4)){
                    v4.setError("Required");
                    return;
                }else{
                    v4.setError(null);
                }

                Intent i=new Intent(register1.this,register2.class);
                i.putExtra("t1",vt1);
                i.putExtra("t2",vt2);
                i.putExtra("t3",vt3);
                i.putExtra("t4",vt4);
                i.putExtra("o1",vo1);
                i.putExtra("o2",vo2);
                i.putExtra("o3",vo3);
                i.putExtra("o4",vo4);
                i.putExtra("v1",vv1);
                i.putExtra("v2",vv2);
                i.putExtra("v3",vv3);
                i.putExtra("v4",vv4);
                i.putExtra("HName",vHName);
                i.putExtra("pass",vpass);
                i.putExtra("email",vemail);
                i.putExtra("phone",vphone);
                startActivity(i);
            }
        });
    }
}