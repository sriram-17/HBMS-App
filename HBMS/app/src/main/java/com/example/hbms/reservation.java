package com.example.hbms;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
public class reservation extends AppCompatActivity {
    DatabaseReference reference;
    String HName;
    RecyclerView recview;
    MyAdapter1 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        if(savedInstanceState==null){
            Bundle extra=getIntent().getExtras();
            if(extra!=null){
                HName=extra.getString("HName");
            }
        }
        Query query=FirebaseDatabase.getInstance().getReference("reservations").orderByChild("hname_status").equalTo(HName+"_Pending");
        Toast.makeText(reservation.this,HName,Toast.LENGTH_SHORT);
        recview=(RecyclerView)findViewById(R.id.recview1);
        recview.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<putPDF> options = new FirebaseRecyclerOptions.Builder<putPDF>()
                .setQuery(query, putPDF.class)
                .build();
        adapter=new MyAdapter1(options,getApplicationContext(),HName);
        recview.setAdapter(adapter);
    }
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}