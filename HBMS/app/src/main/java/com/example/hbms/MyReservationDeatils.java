package com.example.hbms;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MyReservationDeatils extends AppCompatActivity {
    TextView Name,Age,Email,Phone,Symptoms,BedType;
    String key,vname,vemail,vphone,vage,vsymptoms,vbedtype,userID,qrUrl;
    ImageView qrImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation_deatils);
        Name=(TextView)findViewById(R.id.pName1);
        Age=(TextView)findViewById(R.id.pAge1);
        Email=(TextView)findViewById(R.id.pEmail1);
        Phone=(TextView)findViewById(R.id.pPhone1);
        Symptoms=(TextView)findViewById(R.id.pSymptoms1);
        BedType=(TextView)findViewById(R.id.pBedtype1);
        qrImage=(ImageView)findViewById(R.id.qrImage);
        if(savedInstanceState==null){
            Bundle extra=getIntent().getExtras();
            if(extra!=null){
                userID=extra.getString("userID");
                key=extra.getString("key");
            }
        }
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("reservations");
        Query checkUser=reference.orderByChild("key").equalTo(key);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vname=snapshot.child(key).child("name").getValue(String.class);
                vemail=snapshot.child(key).child("email").getValue(String.class);
                vphone=snapshot.child(key).child("phone").getValue(String.class);
                vage=snapshot.child(key).child("age").getValue(String.class);
                vsymptoms=snapshot.child(key).child("symptoms").getValue(String.class);
                vbedtype=snapshot.child(key).child("bedType").getValue(String.class);
                qrUrl=snapshot.child(key).child("qrUrl").getValue(String.class);
                if(qrUrl.equals(""))
                {
                    
                }
                else {
                    Picasso.get().load(qrUrl).into(qrImage);
                }

                Name.setText(vname);
                Email.setText(vemail);
                Phone.setText(vphone);
                Age.setText(vage);
                Symptoms.setText(vsymptoms);
                BedType.setText(vbedtype);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}