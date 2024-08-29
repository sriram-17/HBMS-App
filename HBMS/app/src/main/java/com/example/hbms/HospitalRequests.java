package com.example.hbms;
import android.os.Bundle;
        import android.widget.Toast;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import com.firebase.ui.database.FirebaseRecyclerOptions;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
public class HospitalRequests extends AppCompatActivity {
    DatabaseReference reference;
    String HName;
    RecyclerView recview;
    MyAdapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_requests);
        recview=(RecyclerView)findViewById(R.id.recview2);
        recview.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<HospitalData> options = new FirebaseRecyclerOptions.Builder<HospitalData>()
                .setQuery(FirebaseDatabase.getInstance().getReference("hospitals").orderByChild("status").equalTo("no"), HospitalData.class)
                .build();
        adapter=new MyAdapter2(options,getApplicationContext());
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