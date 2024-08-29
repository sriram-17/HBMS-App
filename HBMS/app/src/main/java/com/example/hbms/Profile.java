package com.example.hbms;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Profile extends AppCompatActivity {

    TextView fName,email,phone,age,gender;
    Button reset,update;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    String userID,vemail;
    DatabaseReference reference;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initialisation
        fName=findViewById(R.id.fName);
        email=findViewById(R.id.email);
        age=findViewById(R.id.age);
        gender=findViewById(R.id.gender);
        phone=findViewById(R.id.phone);
        update=findViewById(R.id.update);
        reset=findViewById(R.id.reset);
        fAuth=FirebaseAuth.getInstance();


        if(savedInstanceState==null){
            Bundle extra=getIntent().getExtras();
            if(extra!=null){
                userID=extra.getString("userID");
            }
        }

        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        View hView =navigationView.getHeaderView(0);
        TextView username=(TextView)hView.findViewById(R.id.username);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        Intent intent1=new Intent(Profile.this, Hospitals.class);
                        intent1.putExtra("userID",userID);
                        startActivity(intent1);
                        break;
                    case R.id.nav_covid:
                        Intent intent2=new Intent(Profile.this,Covid.class);
                        intent2.putExtra("userID",userID);
                        startActivity(intent2);
                        break;
                    case R.id.nav_reservation:
                        Intent intent4=new Intent(Profile.this,MyReservations.class);
                        intent4.putExtra("userID",userID);
                        startActivity(intent4);
                        break;
                    case R.id.nav_vaccine:
                        Intent intent5=new Intent(Profile.this,vaccine.class);
                        startActivity(intent5);
                        break;
                    case R.id.nav_profile:
                        Intent intent3=new Intent(Profile.this,Profile.class);
                        intent3.putExtra("userID",userID);
                        startActivity(intent3);
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(Profile.this,Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        //intent.putExtra("userID",userID);
                        break;

                }
                return false;
            }
        });

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUser=reference.orderByChild("userID").equalTo(userID);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String vfName=snapshot.child(userID).child("fName").getValue(String.class);
                    vemail=snapshot.child(userID).child("email").getValue(String.class);
                    String vphone=snapshot.child(userID).child("phone").getValue(String.class);
                    String vage=snapshot.child(userID).child("age").getValue(String.class);
                    String vgender=snapshot.child(userID).child("gender").getValue(String.class);
                    username.setText(vfName);
                    fName.setText(vfName);
                    email.setText(vemail);
                    phone.setText(vphone);
                    age.setText(vage);
                    gender.setText(vgender);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Profile.this,Updateprofile.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.sendPasswordResetEmail(vemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Profile.this, "Reset Link have been sent to your Mail", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(Profile.this,Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(Profile.this, "Reset Link is not Sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}