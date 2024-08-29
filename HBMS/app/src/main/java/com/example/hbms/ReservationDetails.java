package com.example.hbms;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import org.jetbrains.annotations.NotNull;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static java.lang.String.valueOf;
public class ReservationDetails extends AppCompatActivity {
    Button Report,Accept,cancel;
    TextView Name,Age,Email,Phone,Symptoms,BedType;
    String HName,key,vname,vemail,vphone,vage,vsymptoms,vbedtype,fileUrl,userID;
    Bitmap bitmap;
    StorageReference storageReference;
    String downloadUrl;
    String v1,v2,v3,v4;
    int t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_details);
        Name=(TextView)findViewById(R.id.pName);
        Age=(TextView)findViewById(R.id.pAge);
        Email=(TextView)findViewById(R.id.pEmail);
        Phone=(TextView)findViewById(R.id.pPhone);
        Symptoms=(TextView)findViewById(R.id.pSymptoms);
        BedType=(TextView)findViewById(R.id.pBedtype);
        Accept=(Button)findViewById(R.id.accept);
        cancel=(Button)findViewById(R.id.cancel2);
        Report=(Button)findViewById(R.id.pReport);
        if(savedInstanceState==null){
            Bundle extra=getIntent().getExtras();
            if(extra!=null){
                HName=extra.getString("HName");
                userID=extra.getString("userID");
                key=extra.getString("key");
            }
        }
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("reservations");
        storageReference= FirebaseStorage.getInstance().getReference();
        Query checkUser=reference.orderByChild("hname").equalTo(HName);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vname=snapshot.child(key).child("name").getValue(String.class);
                vemail=snapshot.child(key).child("email").getValue(String.class);
                vphone=snapshot.child(key).child("phone").getValue(String.class);
                vage=snapshot.child(key).child("age").getValue(String.class);
                vsymptoms=snapshot.child(key).child("symptoms").getValue(String.class);
                vbedtype=snapshot.child(key).child("bedType").getValue(String.class);
                fileUrl=snapshot.child(key).child("url").getValue(String.class);
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
        Report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Report.getContext(),viewpdf.class);
                intent.putExtra("fileurl",fileUrl);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Report.getContext().startActivity(intent);
            }
        });
        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(vname+"_"+vage+"_"+vemail+"_"+vphone+"_"+vbedtype+"_"+key, BarcodeFormat.QR_CODE,500,500);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    bitmap = barcodeEncoder.createBitmap(bitMatrix);
                }catch (Exception e){
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] finalimg1 = baos.toByteArray();
                final StorageReference filePath;
                filePath= storageReference.child("QR").child(finalimg1 + "jpg");
                UploadTask uploadTask=filePath.putBytes(finalimg1);
                uploadTask.addOnSuccessListener(ReservationDetails.this,
                        (OnSuccessListener) (taskSnapshot) -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                            downloadUrl = valueOf(uri);
                            insertData();
                        }));

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(key).child("status").setValue("rejected");
                reference.child(key).child("hname_status").setValue(HName+"_rejected");
                reference.child(key).child("userID_status").setValue(userID+"_rejected");
                AlertDialog dlg=new AlertDialog.Builder(ReservationDetails.this).setTitle("Cancelled")
                        .setMessage("Reservation Cancelled")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent3=new Intent(ReservationDetails.this,reservation.class);
                                intent3.putExtra("HName",HName);
                                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                dialog.dismiss();
                                startActivity(intent3);
                            }
                        }).create();
                dlg.show();
            }
        });
    }
    void insertData() {
        DatabaseReference reference1= FirebaseDatabase.getInstance().getReference("reservations");
        reference1.child(key).child("status").setValue("accepted");
        reference1.child(key).child("hname_status").setValue(HName+"_accepted");
        reference1.child(key).child("userID_status").setValue(userID+"_accepted");
        reference1.child(key).child("qrUrl").setValue(downloadUrl);
        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference("hospitals");
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
                    String t1=snapshot.child(HName).child("General").child("total").getValue(String.class);
                    String o1=snapshot.child(HName).child("General").child("occupied").getValue(String.class);
                    String t2=snapshot.child(HName).child("ICU").child("total").getValue(String.class);
                    String o2=snapshot.child(HName).child("ICU").child("occupied").getValue(String.class);
                    String t3=snapshot.child(HName).child("Oxygen").child("total").getValue(String.class);
                    String o3=snapshot.child(HName).child("Oxygen").child("occupied").getValue(String.class);
                    String t4=snapshot.child(HName).child("Ventilator").child("total").getValue(String.class);
                    String o4=snapshot.child(HName).child("Ventilator").child("occupied").getValue(String.class);
                    Calendar calForDate=Calendar.getInstance();
                    SimpleDateFormat currentDate=new SimpleDateFormat("dd-MM-yy");
                    String date=currentDate.format(calForDate.getTime());
                    Calendar calForTime=Calendar.getInstance();
                    SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
                    String time=currentTime.format(calForTime.getTime());
                    switch (vbedtype)
                    {

                        case "General":
                            t=Integer.parseInt(o1);
                            o1=Integer.toString(++t);
                            t=Integer.parseInt(v1);
                            v1=Integer.toString(--t);
                            Data data=new Data(t1,o1,v1,date,time);
                            reference2.child(HName).child("General").setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                            break;
                        case "ICU":
                            t=Integer.parseInt(o2);
                            o2=Integer.toString(++t);
                            t=Integer.parseInt(v2);
                            v2=Integer.toString(--t);
                            Data data1=new Data(t2,o2,v2,date,time);
                            reference2.child(HName).child("ICU").setValue(data1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                            break;
                        case "Oxygen":
                            t=Integer.parseInt(o3);
                            o3=Integer.toString(++t);
                            t=Integer.parseInt(v3);
                            v3=Integer.toString(--t);
                            Data data2=new Data(t1,o1,v1,date,time);
                            reference2.child(HName).child("Oxygen").setValue(data2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                            break;
                        case "Ventilator":
                            t=Integer.parseInt(o4);
                            o4=Integer.toString(++t);
                            t=Integer.parseInt(v4);
                            v4=Integer.toString(--t);
                            Data data4=new Data(t1,o1,v1,date,time);
                            reference2.child(HName).child("General").setValue(data4).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                }
                            });
                            break;
                        }
                    }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        AlertDialog dlg=new AlertDialog.Builder(ReservationDetails.this).setTitle("Booked")
                .setMessage("Bed Confirmed")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2=new Intent(ReservationDetails.this,reservation.class);
                        intent2.putExtra("HName",HName);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        dialog.dismiss();
                        startActivity(intent2);
                    }
                }).create();
        dlg.show();
    }

}