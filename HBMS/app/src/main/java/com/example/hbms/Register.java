package com.example.hbms;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputLayout;

public class Register extends AppCompatActivity {
    TextInputLayout HName,password,email,phone;
    Button next;
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        next=(Button)findViewById(R.id.next);
        HName=(TextInputLayout)findViewById(R.id.HName);
        password=(TextInputLayout)findViewById(R.id.password);
        email=(TextInputLayout)findViewById(R.id.email);
        phone=(TextInputLayout)findViewById(R.id.phone);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login1.class));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vHName=HName.getEditText().getText().toString().trim();
                String vpass=password.getEditText().getText().toString().trim();
                String vemail=email.getEditText().getText().toString().trim();
                String vphone=phone.getEditText().getText().toString().trim();
                if(TextUtils.isEmpty(vHName)){
                    HName.setError("Name is Required");
                    return;
                }else{
                    HName.setError(null);
                }
                if(TextUtils.isEmpty(vpass)){
                    password.setError("Password is Required");
                    return;
                }else if(vpass.length()<6){
                    password.setError("Should not be less than 6 Characters");
                    return;
                }else {
                    password.setError(null);
                }
                if(TextUtils.isEmpty(vemail)){
                    email.setError("Email is Required");
                    return;
                }else if(Patterns.EMAIL_ADDRESS.matcher(vemail).matches()==false){
                    email.setError("Enter valid email");
                    return;
                }else{
                    email.setError(null);
                }

                if(TextUtils.isEmpty(vphone)){
                    phone.setError("Phone isrequired");
                    return;
                }else{
                    phone.setError(null);
                }
                Intent intent=new Intent(Register.this,register1.class);
                intent.putExtra("HName",vHName);
                intent.putExtra("pass",vpass);
                intent.putExtra("email",vemail);
                intent.putExtra("phone",vphone);
                startActivity(intent);


            }
        });


    }
}