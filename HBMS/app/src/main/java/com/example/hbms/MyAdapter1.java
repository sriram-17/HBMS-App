package com.example.hbms;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import org.jetbrains.annotations.NotNull;
import java.util.List;
public class MyAdapter1 extends FirebaseRecyclerAdapter<putPDF,MyAdapter1.myviewholder> {
    Context context;
    List<putPDF> list;
    String HName;

    public MyAdapter1(@NonNull @NotNull FirebaseRecyclerOptions<putPDF> options, Context context, List<putPDF> list,String HName) {
        super(options);
        this.context = context;
        this.list = list;
        this.HName=HName;
    }

    public MyAdapter1(FirebaseRecyclerOptions<putPDF> options, Context applicationContext) {
        super(options);
    }
    public MyAdapter1(FirebaseRecyclerOptions<putPDF> options, Context applicationContext, String HName) {
        super(options);
        this.HName=HName;
    }


    @NonNull
    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MyAdapter1.myviewholder holder, int position, @NonNull @NotNull putPDF model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),ReservationDetails.class);
                intent.putExtra("HName",model.getHname());
                intent.putExtra("userID",model.getUserID());
                intent.putExtra("key",model.getKey());
                v.getContext().startActivity(intent);
            }
        });

    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name,email,phone;
        public myviewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext1);
            email=(TextView)itemView.findViewById(R.id.emailtext1);
            phone=(TextView)itemView.findViewById(R.id.phonetext1);
        }
    }


}