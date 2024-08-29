package com.example.hbms;
import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

        import com.google.android.material.card.MaterialCardView;
        import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class HospitalDashboard extends AppCompatActivity {
    MaterialCardView general;
    MaterialCardView o2;
    MaterialCardView icu;
    MaterialCardView ventilator;
    MaterialCardView reservations;
    MaterialCardView scanner;
    MaterialCardView logout;
    TextInputLayout HName;
    String vHName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);
        HName=findViewById(R.id.HName);
        Intent i=getIntent();
        vHName=i.getStringExtra("HName");
        general=(MaterialCardView)findViewById(R.id.general);
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HospitalDashboard.this,update_beds.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);
            }
        });
        o2=(MaterialCardView)findViewById(R.id.oxygen);
        o2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HospitalDashboard.this,update_o2_beds.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);
            }
        });
        icu=(MaterialCardView)findViewById(R.id.icu);
        icu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HospitalDashboard.this,update_icu_beds.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);
            }
        });
        ventilator=(MaterialCardView)findViewById(R.id.ventilator);
        ventilator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HospitalDashboard.this,ventilator_beds.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);
            }
        });
        reservations=(MaterialCardView)findViewById(R.id.resevations);
        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HospitalDashboard.this,reservation.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);
            }
        });
        scanner=(MaterialCardView)findViewById(R.id.qrScanner);
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HospitalDashboard.this,Scanner.class);
                intent.putExtra("HName",vHName);
                startActivity(intent);

            }
        });
        logout=(MaterialCardView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HospitalDashboard.this,Login1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}