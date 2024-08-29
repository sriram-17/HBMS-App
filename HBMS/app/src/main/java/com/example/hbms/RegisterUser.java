package com.example.hbms;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.util.Log;
        import android.util.Patterns;
        import android.view.View;
        import android.widget.Button;
        import android.widget.RadioButton;
        import android.widget.RadioGroup;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.android.material.textfield.TextInputLayout;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;

        import java.util.HashMap;
        import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    TextInputLayout fName,password,email,phone,age;
    RadioGroup radioGroup;
    RadioButton male,female,other;
    Button register;
    TextView login;
    String userID,vgender;
    String type="user";
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        fName=findViewById(R.id.register_fName);
        password=findViewById(R.id.register_password);
        email=findViewById(R.id.register_email);
        phone=findViewById(R.id.register_phone);
        age=findViewById(R.id.register_age);
        radioGroup=findViewById(R.id.radioGroup);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        other=findViewById(R.id.other);
        register=findViewById(R.id.register);
        login=findViewById(R.id.login);
        fAuth=FirebaseAuth.getInstance();
        rootNode=FirebaseDatabase.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vfName=fName.getEditText().getText().toString().trim();
                String vpass=password.getEditText().getText().toString().trim();
                String vemail=email.getEditText().getText().toString().trim();
                String vphone=phone.getEditText().getText().toString().trim();
                String vage=age.getEditText().getText().toString().trim();

                if(male.isChecked()){
                    vgender=male.getText().toString();
                }else if(female.isChecked()){
                    vgender=female.getText().toString();
                }else if(other.isChecked()){
                    vgender=other.getText().toString();
                }

                if(TextUtils.isEmpty(vfName)){
                    fName.setError("Name is Required");
                    return;
                }else{
                    fName.setError(null);
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

                if(TextUtils.isEmpty(vemail)){
                    email.setError("Email is Required");
                    return;
                }else if(Patterns.EMAIL_ADDRESS.matcher(vemail).matches()==false){
                    email.setError("Enter valid email");
                    return;
                }else{
                    email.setError(null);
                }

                if(TextUtils.isEmpty(vphone)){
                    phone.setError("Phone isrequired");
                    return;
                }else{
                    phone.setError(null);
                }

                if(TextUtils.isEmpty(vage) || vage.length()<=0){
                    age.setError("Enter valid Age");
                    return;
                }else{
                    age.setError(null);
                }

                //Registeration in fire base
                fAuth.createUserWithEmailAndPassword(vemail,vpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterUser.this,"User Created",Toast.LENGTH_SHORT).show();
                            userID=fAuth.getCurrentUser().getUid();
                            reference=rootNode.getReference("users");
                            UserHelper data=new UserHelper(userID,vfName,vpass,vemail,vphone,vage,vgender,type);
                            reference.child(userID).setValue(data);

                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }else{
                            Toast.makeText(RegisterUser.this, "Error ! "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}