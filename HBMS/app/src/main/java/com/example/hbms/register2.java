package com.example.hbms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import org.jetbrains.annotations.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.lang.String.*;

public class register2 extends AppCompatActivity {
    TextInputLayout HName,password,email,phone;
    TextInputLayout Address,latitude,longitude;
    TextInputLayout t1,o1,v1,t2,o2,v2,t3,o3,v3,t4,o4,v4;
    CardView addImage;
    int REQ=1;
    private Bitmap bitmap=null;
    Button Signup ;
    DatabaseReference dbRef,reference,dbRef1;
    StorageReference storageReference;
    String vHName,vpass,vemail,vphone,vaddress,vlatitude,vlongitude,vt1,vt2,vt3,vt4,vo1,vo2,vo3,vo4,vv1,vv2,vv3,vv4;
    String downloadUrl="";
    String status="no";
    ProgressDialog pd;
    FirebaseAuth fauth;
    Uri fileP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        HName=findViewById(R.id.HName);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        t1=findViewById(R.id.totalGeneral);
        t2=findViewById(R.id.totalICU);
        t3=findViewById(R.id.totalO2);
        t4=findViewById(R.id.totalVentilator);
        o1=findViewById(R.id.OccupiedGeneral);
        o2=findViewById(R.id.OccupiedICU);
        o3=findViewById(R.id.OccupiedO2);
        o4=findViewById(R.id.OccupiedVentilator);
        v1=findViewById(R.id.VaccantGeneral);
        v2=findViewById(R.id.VaccantICU);
        v3=findViewById(R.id.VaccantO2);
        v4=findViewById(R.id.VaccantVentilator);
        Address=(TextInputLayout)findViewById(R.id.address);
        latitude=(TextInputLayout)findViewById(R.id.latitude);
        longitude=(TextInputLayout)findViewById(R.id.longitude);
        addImage=findViewById(R.id.image);
        Signup=(Button)findViewById(R.id.register);
        pd=new ProgressDialog(this);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        Intent intent=getIntent();
        vHName=intent.getStringExtra("HName");
        vpass=intent.getStringExtra("pass");
        vemail=intent.getStringExtra("email");
        vphone=intent.getStringExtra("phone");
        vt1=intent.getStringExtra("t1");
        vt2=intent.getStringExtra("t2");
        vt3=intent.getStringExtra("t3");
        vt4=intent.getStringExtra("t4");
        vo1=intent.getStringExtra("o1");
        vo2=intent.getStringExtra("o2");
        vo3=intent.getStringExtra("o3");
        vo4=intent.getStringExtra("o4");
        vv1=intent.getStringExtra("v1");
        vv2=intent.getStringExtra("v2");
        vv3=intent.getStringExtra("v3");
        vv4=intent.getStringExtra("v4");
        reference=FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        fauth=FirebaseAuth.getInstance();
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkValidation();
            }
        });


    }
    void checkValidation()
    {
        vaddress=Address.getEditText().getText().toString().trim();
        vlatitude=latitude.getEditText().getText().toString().trim();
        vlongitude=longitude.getEditText().getText().toString().trim();
        if(TextUtils.isEmpty(vaddress)){
            Address.setError("Address is Required");
            return;
        }else{
            Address.setError(null);
        }
        if(TextUtils.isEmpty(vlatitude)){
            latitude.setError("Latitude is Required");
            return;
        }else{
            latitude.setError(null);
        }
        if(TextUtils.isEmpty(vlongitude)){
            longitude.setError("Address is Required");
            return;
        }else{
            Address.setError(null);
        }
        if(bitmap==null)
        {
            Toast.makeText(register2.this,"Please Insert Image",Toast.LENGTH_SHORT).show();
        }
        else
        {
            insertImage();
        }
    }
    void insertImage() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalimg = baos.toByteArray();
        final StorageReference filePath;
        filePath= storageReference.child("hospitals").child(finalimg + "jpg");
        UploadTask uploadTask=filePath.putBytes(finalimg);
        uploadTask.addOnSuccessListener(register2.this,
                (OnSuccessListener) (taskSnapshot) -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                    downloadUrl = valueOf(uri);
                    insertData();
                }));
    }
    void insertData(){
        dbRef=FirebaseDatabase.getInstance().getReference().child("hospitals");
        HospitalData hospitalData=new HospitalData(vHName,vpass,vemail,vphone,vaddress,vlatitude,vlongitude,downloadUrl,status);
        dbRef.child(vHName).setValue(hospitalData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(register2.this,"Something went wrong Not Updated",Toast.LENGTH_SHORT).show();

            }
        });
        dbRef1=FirebaseDatabase.getInstance().getReference().child("hospitals").child(vHName);
        HospitalData hospitalData1=new HospitalData(vt1,vo1,vv1);
        dbRef1.child("General").setValue(hospitalData1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(register2.this,"Something went wrong Not Updated",Toast.LENGTH_SHORT).show();

            }
        });
        HospitalData hospitalData2=new HospitalData(vt2,vo2,vv3);
        dbRef1.child("ICU").setValue(hospitalData2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(register2.this,"Something went wrong Not Updated",Toast.LENGTH_SHORT).show();

            }
        });
        HospitalData hospitalData3=new HospitalData(vt3,vo3,vv3);
        dbRef1.child("Oxygen").setValue(hospitalData3).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(register2.this,"Something went wrong Not Updated",Toast.LENGTH_SHORT).show();

            }
        });
        HospitalData hospitalData4=new HospitalData(vt4,vo4,vv4);
        dbRef1.child("Ventilator").setValue(hospitalData4).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(register2.this,"Hospital Data sent for Verification",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(register2.this,Login1.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(register2.this,"Something went wrong Not Updated",Toast.LENGTH_SHORT).show();
            }
        });


    }

    void openGallery()
    {
        Intent pickImage=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK)
        {
            fileP=data.getData();
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),fileP);
                Toast.makeText(register2.this,"Image Uploaded",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}