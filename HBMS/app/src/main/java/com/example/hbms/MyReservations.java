package com.example.hbms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MyReservations extends AppCompatActivity {
    String userID;
    RecyclerView recview;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    MyAdapter3 adapter;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    int count;
    TextView reserve;
    TextView reserve1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);
            if(savedInstanceState==null) {
                Bundle extra = getIntent().getExtras();
                if (extra != null) {
                    userID = extra.getString("userID");
                }
            }
            setUpToolbar();

        RecyclerView.LayoutManager firstLayoutManager = new LinearLayoutManager(this);
            recview=(RecyclerView)findViewById(R.id.recview3);
            recview.setLayoutManager(firstLayoutManager);
        FirebaseRecyclerOptions<putPDF> options = new FirebaseRecyclerOptions.Builder<putPDF>()
                .setQuery(FirebaseDatabase.getInstance().getReference("reservations").orderByChild("userID").equalTo(userID),putPDF.class)
                .build();
        adapter=new MyAdapter3(options,this,userID);
        recview.setAdapter(adapter);
        reserve=(TextView)findViewById(R.id.reserveCount);
        databaseReference=FirebaseDatabase.getInstance().getReference("reservations");
        Query checkUser=databaseReference.orderByChild("userID_status").equalTo(userID+"_accepted");
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    count=(int)snapshot.getChildrenCount();
                    reserve.setText("Number of Reservations: "+count);
                }
                else{
                    reserve.setText("Number of Reservations: 0");

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });




        reserve1=(TextView)findViewById(R.id.reserveCount1);
        databaseReference1=FirebaseDatabase.getInstance().getReference("reservations");
        Query checkUser2=databaseReference1.orderByChild("userID_status").equalTo(userID+"_Pending");
        checkUser2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    count=(int)snapshot.getChildrenCount();
                    reserve1.setText("Number of Pending Reservations: "+count);
                }
                else{
                    reserve1.setText("Number of Pending Reservations: 0");

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });












        navigationView = (NavigationView) findViewById(R.id.navigation_menu2);
        View hView =navigationView.getHeaderView(0);
        TextView username=(TextView)hView.findViewById(R.id.username);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_home:
                        Intent intent1=new Intent(MyReservations.this, Hospitals.class);
                        intent1.putExtra("userID",userID);
                        startActivity(intent1);
                        break;
                    case R.id.nav_covid:
                        Intent intent2=new Intent(MyReservations.this,Covid.class);
                        intent2.putExtra("userID",userID);
                        startActivity(intent2);
                        break;
                    case R.id.nav_reservation:
                        Intent intent4=new Intent(MyReservations.this,MyReservations.class);
                        intent4.putExtra("userID",userID);
                        startActivity(intent4);
                        break;
                    case R.id.nav_vaccine:
                        Intent intent5=new Intent(MyReservations.this,vaccine.class);
                        startActivity(intent5);
                        break;
                    case R.id.nav_profile:
                        Intent intent3=new Intent(MyReservations.this,Profile.class);
                        intent3.putExtra("userID",userID);
                        startActivity(intent3);
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(MyReservations.this,Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });
        DatabaseReference databaseReference1= FirebaseDatabase.getInstance().getReference("users");
        Query checkUser1=databaseReference1.orderByChild("userID").equalTo(userID);
        checkUser1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String vfName=snapshot.child(userID).child("fName").getValue(String.class);
                username.setText(vfName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout2);
        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}