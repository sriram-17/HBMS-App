package com.example.hbms;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
public class dashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Button locate,reserve;
    FirebaseAuth fAuth;
    DatabaseReference reference,reference1;
    String userID,HName;
    TextView name1 ,totGen,occGen,vacGen,totICU,occICU,vacICU,totO2,occO2,vacO2,totVen,occVen,vacVen;
    ImageView img;
    String longitude,latitude;
    String v1,v2,v3,v4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //Initialization of Variable and Object instantiation
        locate=findViewById(R.id.locate);
        reserve=findViewById(R.id.reserve);
        name1=(TextView)findViewById(R.id.name);
        img=(ImageView)findViewById(R.id.HospitalImage);
        totGen=(TextView)findViewById(R.id.total_gen);
        occGen=(TextView)findViewById(R.id.occupied_gen);
        vacGen=(TextView)findViewById(R.id.vacant_gen);
        totICU=(TextView)findViewById(R.id.total_icu);
        occICU=(TextView)findViewById(R.id.occupied_icu);
        vacICU=(TextView)findViewById(R.id.vacant_icu);
        totO2=(TextView)findViewById(R.id.total_o2);
        occO2=(TextView)findViewById(R.id.occupied_o2);
        vacO2=(TextView)findViewById(R.id.vacant_o2);
        totVen=(TextView)findViewById(R.id.total_ven);
        occVen=(TextView)findViewById(R.id.occupied_ven);
        vacVen=(TextView)findViewById(R.id.vacant_ven);
        Intent i=getIntent();
        userID=i.getStringExtra("userID");
        HName=i.getStringExtra("hname");
        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        View hView =navigationView.getHeaderView(0);
        TextView username=(TextView)hView.findViewById(R.id.username);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:
                        Intent intent1=new Intent(dashboard.this, Hospitals.class);
                        intent1.putExtra("userID",userID);
                        startActivity(intent1);
                        break;
                    case R.id.nav_covid:
                        Intent intent2=new Intent(dashboard.this,Covid.class);
                        intent2.putExtra("userID",userID);
                        startActivity(intent2);
                        break;
                    case R.id.nav_reservation:
                        Intent intent4=new Intent(dashboard.this,MyReservations.class);
                        intent4.putExtra("userID",userID);
                        startActivity(intent4);
                        break;
                    case R.id.nav_vaccine:
                        Intent intent5=new Intent(dashboard.this,vaccine.class);
                        startActivity(intent5);
                        break;
                    case R.id.nav_profile:
                        Intent intent3=new Intent(dashboard.this,Profile.class);
                        intent3.putExtra("userID",userID);
                        startActivity(intent3);
                        break;
                    case R.id.nav_logout:
                        FirebaseAuth.getInstance().signOut();
                        Intent intent=new Intent(dashboard.this,Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });
        reference1=FirebaseDatabase.getInstance().getReference("hospitals");
        Query check=reference1.orderByChild("hname").equalTo(HName);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String addr=snapshot.child(HName).child("address").getValue(String.class);
                    String url=snapshot.child(HName).child("image").getValue(String.class);
                    Picasso.get().load(url).into(img);
                    name1.setText(HName+'\n'+addr);
                    longitude=snapshot.child(HName).child("longitude").getValue(String.class);
                    latitude=snapshot.child(HName).child("latitude").getValue(String.class);
                    String t1=snapshot.child(HName).child("General").child("total").getValue(String.class);
                    String o1=snapshot.child(HName).child("General").child("occupied").getValue(String.class);
                    v1=snapshot.child(HName).child("General").child("vaccant").getValue(String.class);
                    totGen.setText("Total\n"+t1);
                    occGen.setText("Occupied\n"+o1);
                    vacGen.setText("Vaccant\n"+v1);
                    String t2=snapshot.child(HName).child("ICU").child("total").getValue(String.class);
                    String o2=snapshot.child(HName).child("ICU").child("occupied").getValue(String.class);
                    v2=snapshot.child(HName).child("ICU").child("vaccant").getValue(String.class);
                    totICU.setText("Total\n"+t2);
                    occICU.setText("Occupied\n"+o2);
                    vacICU.setText("Vaccant\n"+v2);
                    String t3=snapshot.child(HName).child("Oxygen").child("total").getValue(String.class);
                    String o3=snapshot.child(HName).child("Oxygen").child("occupied").getValue(String.class);
                    v3=snapshot.child(HName).child("Oxygen").child("vaccant").getValue(String.class);
                    totO2.setText("Total\n"+t3);
                    occO2.setText("Occupied\n"+o3);
                    vacO2.setText("Vaccant\n"+v3);
                    String t4=snapshot.child(HName).child("Ventilator").child("total").getValue(String.class);
                    String o4=snapshot.child(HName).child("Ventilator").child("occupied").getValue(String.class);
                    v4=snapshot.child(HName).child("Ventilator").child("vaccant").getValue(String.class);
                    totVen.setText("Total\n"+t4);
                    occVen.setText("Occupied\n"+o4);
                    vacVen.setText("Vaccant\n"+v4);
                    if("0".equals(v1) &&"0".equals(v2) &&"0".equals(v3) &&"0".equals(v4))
                    {
                        reserve.setEnabled(false);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

        locate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                Intent intent1 = intent.setData(Uri.parse("geo:"+latitude+","+longitude));
                Intent chooser=Intent.createChooser(intent,"Launch Maps");
                startActivity(chooser);
                //finish();
            }
        });

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(dashboard.this,Booking.class);
               intent.putExtra("userID",userID);
               intent.putExtra("hname",HName);
               startActivity(intent);
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