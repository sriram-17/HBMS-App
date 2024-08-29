package com.example.hbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.*;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Updateprofile extends AppCompatActivity {

    TextInputLayout fName,password,email,phone,age;
    RadioGroup radioGroup;
    RadioButton male,female,other;
    Button update,cancel;
    String userID,vgender;


    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

        fName=findViewById(R.id.fName);
        phone=findViewById(R.id.phone);
        age=findViewById(R.id.age);
        radioGroup=findViewById(R.id.radioGroup);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        other=findViewById(R.id.other);

        update=findViewById(R.id.update);
        cancel=findViewById(R.id.cancel);

        if(savedInstanceState==null){
            Bundle extra=getIntent().getExtras();
            if(extra!=null){
                userID=extra.getString("userID");
            }
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vfName=fName.getEditText().getText().toString().trim();
                String vphone=phone.getEditText().getText().toString().trim();
                String vage=age.getEditText().getText().toString().trim();
                if(male.isChecked()){
                    vgender=male.getText().toString();
                }else if(female.isChecked()){
                    vgender=female.getText().toString();
                }else if(other.isChecked()){
                    vgender=other.getText().toString();
                }

                if(TextUtils.isEmpty(vfName)){
                    fName.setError("Name is Required");
                    return;
                }else{
                    fName.setError(null);
                }


                if(TextUtils.isEmpty(vphone)){
                    phone.setError("Phone isrequired");
                    return;
                }else{
                    phone.setError(null);
                }

                if(TextUtils.isEmpty(vage) || vage.length()<=0){
                    age.setError("Enter valid Age");
                    return;
                }else{
                    age.setError(null);
                }

                reference=FirebaseDatabase.getInstance().getReference("users");
                reference.child(userID).child("fName").setValue(fName.getEditText().getText().toString().trim());
                reference.child(userID).child("phone").setValue(phone.getEditText().getText().toString().trim());
                reference.child(userID).child("age").setValue(age.getEditText().getText().toString().trim());
                Toast.makeText(Updateprofile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Updateprofile.this,Profile.class);
                intent.putExtra("userID",userID);
                startActivity(intent);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Updateprofile.this, "Profile Update Cancelled", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Updateprofile.this,Profile.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
    }
}