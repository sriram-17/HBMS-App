package com.example.hbms;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
public class Login1 extends AppCompatActivity {
    TextInputLayout email,password,hname;
    Button login;
    TextView signup;
    String vpass,vemail,vhname;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        hname=findViewById(R.id.hname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

    }
    public void checkValidation()
    {
        vpass=password.getEditText().getText().toString().trim();
        vemail=email.getEditText().getText().toString().trim();
        vhname=hname.getEditText().getText().toString().trim();

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
            UserLogin();
        }
    }
    public void UserLogin()
    {
        reference= FirebaseDatabase.getInstance().getReference("hospitals");
        Query checkUser=reference.orderByChild("hname").equalTo(vhname);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    hname.setError(null);
                    hname.setErrorEnabled(false);
                    String passwordDB=snapshot.child(vhname).child("password").getValue(String.class);
                    if(passwordDB.equals(vpass))
                    {
                        hname.setError(null);
                        hname.setErrorEnabled(false);
                        String status=snapshot.child(vhname).child("status").getValue(String.class);
                        if("yes".equals(status)){
                            Intent intent=new Intent(Login1.this,HospitalDashboard.class);
                            intent.putExtra("HName",vhname);
                            startActivity(intent);
                        }
                        else{
                            AlertDialog dlg=new AlertDialog.Builder(Login1.this).setTitle("Error")
                                    .setMessage("Hospital not yet Verified")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent(Login1.this,Login1.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            dialog.dismiss();
                                            startActivity(intent);
                                        }
                                    }).create();
                            dlg.show();

                        }
                    }
                    else
                    {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else
                {
                    hname.setError("Wrong Username");
                    hname.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}