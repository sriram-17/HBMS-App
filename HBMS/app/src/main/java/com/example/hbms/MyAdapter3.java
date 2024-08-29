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
public class MyAdapter3 extends FirebaseRecyclerAdapter<putPDF,MyAdapter3.myviewholder> {
    Context context;
    List<putPDF> list;
    String userID;

    public MyAdapter3(@NonNull @NotNull FirebaseRecyclerOptions<putPDF> options, Context context, List<putPDF> list,String userID) {
        super(options);
        this.context = context;
        this.list = list;
        this.userID=userID;
    }

    public MyAdapter3(FirebaseRecyclerOptions<putPDF> options, Context applicationContext) {
        super(options);
    }

    public MyAdapter3(@NonNull @NotNull FirebaseRecyclerOptions<putPDF> options, Context applicationContext, String userID) {
        super(options);
        this.userID = userID;
    }

    @NonNull
    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row1,parent,false);
        return new myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MyAdapter3.myviewholder holder, int position, @NonNull @NotNull putPDF model) {
        holder.name.setText(model.getName());
        holder.hname.setText(model.getHname());
        holder.bedType.setText(model.getBedType());
        holder.status.setText("Status: "+model.getStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),MyReservationDeatils.class);
                intent.putExtra("userID",userID);
                intent.putExtra("key",model.getKey());
                v.getContext().startActivity(intent);
            }
        });

    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name,hname,bedType,status;
        public myviewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.nametext2);
            hname=(TextView)itemView.findViewById(R.id.hname2);
            bedType=(TextView)itemView.findViewById(R.id.bedType2);
            status=(TextView)itemView.findViewById(R.id.status);
        }
    }


}