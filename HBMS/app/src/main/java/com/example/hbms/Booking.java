package com.example.hbms;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Nullable;
public class Booking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String type[]={"General","ICU","Oxygen","Ventilator"};
    TextInputLayout fName,age,symptom;
    Button book,cancel,pick;
    String userID,HName,v1,v2,v3,v4;
    DatabaseReference reference;
    StorageReference storageReference;
    Uri pdfData;
    String vfName,vage,vsymptom,vbedType,email,phone,status,hname_status,userID_status,qrUrl;
    ArrayAdapter<String> arrayadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        status="Pending";
        qrUrl="";
        fName=findViewById(R.id.fName);
        age=findViewById(R.id.age);
        symptom=findViewById(R.id.symptom);
        book=findViewById(R.id.book);
        cancel=findViewById(R.id.cancel);
        pick=findViewById(R.id.pick);
        Intent i=getIntent();
        userID=i.getStringExtra("userID");
        HName=i.getStringExtra("hname");
        userID_status=userID+"_"+status;
        hname_status=HName+"_"+status;
        storageReference=FirebaseStorage.getInstance().getReference();
        reference= FirebaseDatabase.getInstance().getReference("reservations");
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vfName=fName.getEditText().getText().toString().trim();
                vage=age.getEditText().getText().toString().trim();;
                vsymptom=symptom.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(vfName)){
                    fName.setError("Name is Required");
                    return;
                }else{
                    fName.setError(null);
                }

                if(TextUtils.isEmpty(vage) || vage.length()<=0){
                    age.setError("Enter valid Age");
                    return;
                }else{
                    age.setError(null);
                }
                uploadPDF();
                AlertDialog dlg=new AlertDialog.Builder(Booking.this).setTitle("Success")
                        .setMessage("Request Sent...Wait for Confirmation")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(Booking.this,dashboard.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("hname",HName);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                dialog.dismiss();
                                startActivity(intent);
                            }
                        }).create();
                dlg.show();
            }
        });
        DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("users");
        Query data=reference1.orderByChild("userID").equalTo(userID);
        data.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    email=snapshot.child(userID).child("email").getValue(String.class);
                    phone=snapshot.child(userID).child("phone").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        DatabaseReference reference2= FirebaseDatabase.getInstance().getReference("hospitals");
        Query data1=reference2.orderByChild("hname").equalTo(HName);
        data1.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    v1=snapshot.child(HName).child("General").child("vaccant").getValue(String.class);
                    v2=snapshot.child(HName).child("ICU").child("vaccant").getValue(String.class);
                    v3=snapshot.child(HName).child("Oxygen").child("vaccant").getValue(String.class);
                    v4=snapshot.child(HName).child("Ventilator").child("vaccant").getValue(String.class);
                    Spinner spinner=findViewById(R.id.spinner);
                    spinner.setOnItemSelectedListener(Booking.this);
                    arrayadapter = new ArrayAdapter<String>(Booking.this,android.R.layout.simple_spinner_item,type){
                        @Override
                        public boolean isEnabled(int position){
                            if("0".equals(v1)&&position == 0)
                            {
                                return false;
                            }
                            else if("0".equals(v2)&&position == 1)
                            {
                                return false;
                            }
                            else if("0".equals(v3)&&position == 2)
                            {
                                return false;
                            }
                            else if("0".equals(v4)&&position == 3)
                            {
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView, ViewGroup parent)
                        {
                            View spinnerview = super.getDropDownView(position, convertView, parent);

                            TextView spinnertextview = (TextView) spinnerview;

                            if("0".equals(v1)&&position == 0) {

                                //Set the disable spinner item color fade .
                                spinnertextview.setTextColor(Color.parseColor("#bcbcbb"));
                            }
                            else if("0".equals(v2)&&position == 1) {

                                //Set the disable spinner item color fade .
                                spinnertextview.setTextColor(Color.parseColor("#bcbcbb"));
                            }
                            else if("0".equals(v3)&&position == 2) {

                                //Set the disable spinner item color fade .
                                spinnertextview.setTextColor(Color.parseColor("#bcbcbb"));
                            }
                            else if("0".equals(v4)&&position == 3) {

                                //Set the disable spinner item color fade .
                                spinnertextview.setTextColor(Color.parseColor("#bcbcbb"));
                            }
                            else {

                                spinnertextview.setTextColor(Color.BLACK);

                            }
                            return spinnerview;
                        }
                    };
                    arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(arrayadapter);

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Booking.this,dashboard.class);
                intent.putExtra("userID",userID);
                intent.putExtra("hname",HName);
                startActivity(intent);
            }
        });
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                          .withListener(new PermissionListener() {
                              @Override
                              public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                  Intent intent=new Intent();
                                  intent.setType("application/pdf");
                                  intent.setAction(Intent.ACTION_GET_CONTENT);
                                  startActivityForResult(Intent.createChooser(intent,"Select Pdf Files"),12);
                              }

                              @Override
                              public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                              }

                              @Override
                              public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                  permissionToken.continuePermissionRequest();
                              }
                          }).check();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        vbedType=type[position];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            pdfData=data.getData();
            Toast.makeText(this,""+pdfData,Toast.LENGTH_SHORT).show();

        }
    }

    void uploadPDF()
    {
        StorageReference storage=storageReference.child("resevations").child("pdf"+System.currentTimeMillis()+".pdf");
        storage.putFile(pdfData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri uri=uriTask.getResult();
                String uri1=uri.toString();
                String key=reference.push().getKey();
                putPDF putPDF=new putPDF(HName,vfName,email,phone,userID,vage,vbedType,vsymptom,uri1,key,status,userID_status,hname_status,qrUrl);
                reference.child(key).setValue(putPDF);

            }
        });
    }

}