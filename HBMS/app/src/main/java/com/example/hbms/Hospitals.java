package com.example.hbms;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Hospitals extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    String userID;
    TextView  username,name;
    RecyclerView recview;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitals);
        if(savedInstanceState==null){
            Bundle extra=getIntent().getExtras();
            if(extra!=null){
                userID=extra.getString("userID");
            }
        }
        setUpToolbar();

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("hospitals").orderByChild("status").equalTo("yes"), Model.class)
                        .build();
        adapter=new MyAdapter(options,getApplicationContext(),userID);
        recview.setAdapter(adapter);
        navigationView = (NavigationView) findViewById(R.id.navigation_menu1);
        View hView =navigationView.getHeaderView(0);
        TextView username=(TextView)hView.findViewById(R.id.username);
        reference= FirebaseDatabase.getInstance().getReference("users");
        Query checkUser=reference.orderByChild("userID").equalTo(userID);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String vfName=snapshot.child(userID).child("fName").getValue(String.class);
                username.setText(vfName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        Intent intent1=new Intent(Hospitals.this, Hospitals.class);
                        intent1.putExtra("userID",userID);
                        startActivity(intent1);
                        break;
                    case R.id.nav_covid:
                        Intent intent2=new Intent(Hospitals.this,Covid.class);
                        intent2.putExtra("userID",userID);
                        startActivity(intent2);
                        break;
                    case R.id.nav_reservation:
                        Intent intent4=new Intent(Hospitals.this,MyReservations.class);
                        intent4.putExtra("userID",userID);
                        startActivity(intent4);
                        break;
                    case R.id.nav_vaccine:
                        Intent intent5=new Intent(Hospitals.this,vaccine.class);
                        startActivity(intent5);
                        break;
                    case R.id.nav_profile:
                        Intent intent3=new Intent(Hospitals.this,Profile.class);
                        intent3.putExtra("userID",userID);
                        startActivity(intent3);
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(Hospitals.this,Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });
    }
    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout1);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    void  processSearch(String s)
    {
        FirebaseRecyclerOptions<Model> options = new FirebaseRecyclerOptions.Builder<Model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("hospitals").orderByChild("hname").startAt(s).endAt(s+"\uf8ff"), Model.class)
                .build();
        adapter=new MyAdapter(options,getApplicationContext());
        adapter.startListening();
        recview.setAdapter(adapter);
    }
}










