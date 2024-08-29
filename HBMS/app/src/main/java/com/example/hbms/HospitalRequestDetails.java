package com.example.hbms;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
public class HospitalRequestDetails extends AppCompatActivity {
    TextView totGen,occGen,vacGen,totICU,occICU,vacICU,totO2,occO2,vacO2,totVen,occVen,vacVen;
    TextView Name,Email,Phone,Latitude,Longitude,Address;
    String HName;
    DatabaseReference reference1;
    ImageView img;
    Button accept,reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_request_details);
        img=(ImageView)findViewById(R.id.Himage);
        Name=(TextView)findViewById(R.id.HName1);
        Email=(TextView)findViewById(R.id.HEmail1);
        Phone=(TextView)findViewById(R.id.Hphone1);
        Address=(TextView)findViewById(R.id.HAddress);
        Latitude=(TextView)findViewById(R.id.HLatitude);
        Longitude=(TextView)findViewById(R.id.HLongitude);
        totGen=(TextView)findViewById(R.id.total_gen1);
        occGen=(TextView)findViewById(R.id.occupied_gen1);
        vacGen=(TextView)findViewById(R.id.vacant_gen1);
        totICU=(TextView)findViewById(R.id.total_icu1);
        occICU=(TextView)findViewById(R.id.occupied_icu1);
        vacICU=(TextView)findViewById(R.id.vacant_icu1);
        totO2=(TextView)findViewById(R.id.total_o21);
        occO2=(TextView)findViewById(R.id.occupied_o21);
        vacO2=(TextView)findViewById(R.id.vacant_o21);
        totVen=(TextView)findViewById(R.id.total_ven1);
        occVen=(TextView)findViewById(R.id.occupied_ven1);
        vacVen=(TextView)findViewById(R.id.vacant_ven1);
        accept=(Button)findViewById(R.id.accept1);
        reject=(Button)findViewById(R.id.reject1);
        Intent i=getIntent();
        HName=i.getStringExtra("hname");
        reference1= FirebaseDatabase.getInstance().getReference("hospitals");
        Query check=reference1.orderByChild("hname").equalTo(HName);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String url=snapshot.child(HName).child("image").getValue(String.class);
                    Glide.with(img.getContext()).load(url).into(img);
                    String name=snapshot.child(HName).child("hname").getValue(String.class);
                    String email=snapshot.child(HName).child("email").getValue(String.class);
                    String phone=snapshot.child(HName).child("phone").getValue(String.class);
                    String addr=snapshot.child(HName).child("address").getValue(String.class);
                    String longitude=snapshot.child(HName).child("longitude").getValue(String.class);
                    String latitude=snapshot.child(HName).child("latitude").getValue(String.class);
                    Name.setText(name);
                    Email.setText(email);
                    Phone.setText(phone);
                    Address.setText(addr);
                    Latitude.setText(latitude);
                    Longitude.setText(longitude);
                    String t1=snapshot.child(HName).child("General").child("total").getValue(String.class);
                    String o1=snapshot.child(HName).child("General").child("occupied").getValue(String.class);
                    String v1=snapshot.child(HName).child("General").child("vaccant").getValue(String.class);
                    totGen.setText("Total\n"+t1);
                    occGen.setText("Occupied\n"+o1);
                    vacGen.setText("Vaccant\n"+v1);
                    String t2=snapshot.child(HName).child("ICU").child("total").getValue(String.class);
                    String o2=snapshot.child(HName).child("ICU").child("occupied").getValue(String.class);
                    String v2=snapshot.child(HName).child("ICU").child("vaccant").getValue(String.class);
                    totICU.setText("Total\n"+t2);
                    occICU.setText("Occupied\n"+o2);
                    vacICU.setText("Vaccant\n"+v2);
                    String t3=snapshot.child(HName).child("Oxygen").child("total").getValue(String.class);
                    String o3=snapshot.child(HName).child("Oxygen").child("occupied").getValue(String.class);
                    String v3=snapshot.child(HName).child("Oxygen").child("vaccant").getValue(String.class);
                    totO2.setText("Total\n"+t3);
                    occO2.setText("Occupied\n"+o3);
                    vacO2.setText("Vaccant\n"+v3);
                    String t4=snapshot.child(HName).child("Ventilator").child("total").getValue(String.class);
                    String o4=snapshot.child(HName).child("Ventilator").child("occupied").getValue(String.class);
                    String v4=snapshot.child(HName).child("Ventilator").child("vaccant").getValue(String.class);
                    totVen.setText("Total\n"+t4);
                    occVen.setText("Occupied\n"+o4);
                    vacVen.setText("Vaccant\n"+v4);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference1.child(HName).child("status").setValue("yes");


                AlertDialog dlg=new AlertDialog.Builder(HospitalRequestDetails.this).setTitle("Success")
                        .setMessage("Hospital Verified and Accepted")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(HospitalRequestDetails.this,HospitalRequests.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                dialog.dismiss();
                                startActivity(intent);
                            }
                        }).create();
                dlg.show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference1.child(HName).child("status").setValue("rejected");
                AlertDialog dlg=new AlertDialog.Builder(HospitalRequestDetails.this).setTitle("Success")
                        .setMessage("Hospital Verified and Rejected")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(HospitalRequestDetails.this,HospitalRequests.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                dialog.dismiss();
                                startActivity(intent);
                            }
                        }).create();
                dlg.show();
            }
        });

    }
}