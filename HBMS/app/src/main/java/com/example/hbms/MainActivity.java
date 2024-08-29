package com.example.hbms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    Button logout;
    TextView fName,age,gender,phone,email,bgroup;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fName= findViewById(R.id.fName);
        age=findViewById(R.id.age);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        gender=findViewById(R.id.gender);
        bgroup=findViewById(R.id.bGroup);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        userID= fAuth.getCurrentUser().getUid();

        DocumentReference documentReference= fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable  DocumentSnapshot documentSnapshot, @Nullable  FirebaseFirestoreException error) {
                fName.setText(documentSnapshot.getString("fName"));
                age.setText(documentSnapshot.getString("age"));
                email.setText(documentSnapshot.getString("email"));
                phone.setText(documentSnapshot.getString("phone"));
                gender.setText(documentSnapshot.getString("gender"));
                bgroup.setText(documentSnapshot.getString("bloodgroup"));


            }
        });



    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}