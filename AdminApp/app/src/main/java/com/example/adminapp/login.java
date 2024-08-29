package com.example.adminapp;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class login extends AppCompatActivity {
    TextInputLayout email,password,hname;
    Button login;
    TextView signup;
    String vpass,vemail,vhname;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hname=findViewById(R.id.hname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });

    }
    public void checkValidation()
    {
            vpass=password.getEditText().getText().toString().trim();
            vemail=email.getEditText().getText().toString().trim();
            vhname=hname.getEditText().getText().toString().trim();

            if(TextUtils.isEmpty(vemail)){
                email.setError("Email is Required");
                return;
            }else if(Patterns.EMAIL_ADDRESS.matcher(vemail).matches()==false){
                email.setError("Enter valid email");
                return;
            }else{
                email.setError(null);
            }

            if(TextUtils.isEmpty(vpass)){
                password.setError("Password is Required");
                return;
            }else if(vpass.length()<6){
                password.setError("Should not be less than 6 Characters");
                return;
            }else {
                password.setError(null);
                UserLogin();
            }
    }
    public void UserLogin()
    {
      reference=FirebaseDatabase.getInstance().getReference("hospitals");
      Query checkUser=reference.orderByChild("hname").equalTo(vhname);
      checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
              if(snapshot.exists())
              {
                  hname.setError(null);
                  hname.setErrorEnabled(false);
                  String passwordDB=snapshot.child(vhname).child("password").getValue(String.class);
                  if(passwordDB.equals(vpass))
                  {
                      hname.setError(null);
                      hname.setErrorEnabled(false);
                      Intent intent=new Intent(login.this,MainActivity.class);
                      intent.putExtra("HName",vhname);
                      startActivity(intent);
                  }
                  else
                  {
                      password.setError("Wrong Password");
                      password.requestFocus();
                  }
              }
              else
              {
                  hname.setError("Wrong Username");
                  hname.requestFocus();
              }
          }

          @Override
          public void onCancelled(@NonNull @NotNull DatabaseError error) {

          }
      });
    }


}