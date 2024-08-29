package com.example.hbms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextInputLayout email,password;
    Button login;
    TextView signup,forgot;

    FirebaseAuth fAuth;
    DatabaseReference reference;
    String userID;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        forgot=findViewById(R.id.forgot);

        fAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vpass=password.getEditText().getText().toString().trim();
                String vemail=email.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(vemail)){
                    email.setError("Email is Required");
                    return;
                }else if(Patterns.EMAIL_ADDRESS.matcher(vemail).matches()==false){
                    email.setError("Enter valid email");
                    return;
                }else{
                    email.setError(null);
                }

                if(TextUtils.isEmpty(vpass)){
                    password.setError("Password is Required");
                    return;
                }else if(vpass.length()<6){
                    password.setError("Should not be less than 6 Characters");
                    return;
                }else {
                    password.setError(null);
                }

                fAuth.signInWithEmailAndPassword(vemail,vpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userID=fAuth.getCurrentUser().getUid();
                            Toast.makeText(Login.this,"Login success",Toast.LENGTH_SHORT).show();
                            reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.child(userID).child("password").setValue(vpass);
                            userType();
                        }else{
                            Toast.makeText(Login.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterUser.class));
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText reset=new EditText(v.getContext());
                AlertDialog.Builder resetDialog = new AlertDialog.Builder(v.getContext());
                resetDialog.setTitle("Reset Password ?");
                resetDialog.setMessage("Enter your Email To receive Reset Link.");
                resetDialog.setView(reset);

                resetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            //reset the mail using link
                        String mail=reset.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Login.this, "Reset Link have been sent to your Mail", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Reset Link is not Sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                resetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            //close the dialog
                    }
                });

                resetDialog.create().show();
            }
        });

    }

    void userType() {
        Query checkUser=reference.orderByChild("userID").equalTo(userID);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                type=snapshot.child(userID).child("type").getValue(String.class);
                if(type!=null && "admin".equalsIgnoreCase(type)){
                    Intent intent=new Intent(Login.this,AdminActivity.class);
                    intent.putExtra("userID",userID);
                    startActivity(intent);
                }
                if(type!=null &&"user".equalsIgnoreCase(type)) {
                    Intent intent1 = new Intent(Login.this, Hospitals.class);
                    intent1.putExtra("userID", userID);
                    startActivity(intent1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}