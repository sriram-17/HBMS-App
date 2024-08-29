package com.example.hbms;
import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.material.textfield.TextInputLayout;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.Query;
        import com.google.firebase.database.ValueEventListener;

        import org.jetbrains.annotations.NotNull;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;
public class update_beds extends AppCompatActivity {
    EditText total,occupied,vaccant;
    Button update;
    private DatabaseReference reference;
    private ProgressDialog pd;
    TextInputLayout HName;
    String vHName,total1,occupied1,vaccant1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_beds);
        total=findViewById(R.id.total);
        occupied=findViewById(R.id.occupied);
        vaccant=findViewById(R.id.vaccant);
        update=findViewById(R.id.update);
        HName=findViewById(R.id.HName);
        Intent intent=getIntent();
        vHName=intent.getStringExtra("HName");
        reference= FirebaseDatabase.getInstance().getReference("hospitals");
        showAllData();
        pd=new ProgressDialog(this);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total1= total.getText().toString();
                occupied1= occupied.getText().toString();
                vaccant1= vaccant.getText().toString();
                updateData();
            }
        });
    }
    void showAllData()
    {
        Query data=reference.orderByChild("hname").equalTo(vHName);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    String t1=snapshot.child(vHName).child("General").child("total").getValue(String.class);
                    String o1=snapshot.child(vHName).child("General").child("occupied").getValue(String.class);
                    String v1=snapshot.child(vHName).child("General").child("vaccant").getValue(String.class);
                    total.setText(t1);
                    occupied.setText(o1);
                    vaccant.setText(v1);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
    private void updateData()
    {
        pd.setMessage("Updating.....");
        pd.show();
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MM-yy");
        String date=currentDate.format(calForDate.getTime());
        Calendar calForTime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
        String time=currentTime.format(calForTime.getTime());
        Data data=new Data(total1,occupied1,vaccant1,date,time);
        reference.child(vHName).child("General").setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd.dismiss();
                Toast.makeText(update_beds.this,"Values Updated",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(update_beds.this,"Something went wrong Not Updated",Toast.LENGTH_SHORT).show();

            }
        });

    }
}