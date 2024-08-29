package com.example.hbms;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import org.jetbrains.annotations.NotNull;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter2 extends FirebaseRecyclerAdapter<HospitalData,MyAdapter2.myviewholder> {
    Context context;
    List<HospitalData> list;

    public MyAdapter2(@NonNull @NotNull FirebaseRecyclerOptions<HospitalData> options, Context context, List<HospitalData> list) {
        super(options);
        this.context = context;
        this.list = list;
    }

    public MyAdapter2(FirebaseRecyclerOptions<HospitalData> options, Context applicationContext) {
        super(options);
    }


    @NonNull
    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MyAdapter2.myviewholder holder, int position, @NonNull @NotNull HospitalData model) {
        holder.name.setText(model.getHName());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
        holder.address.setText(model.getAddress());
        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),HospitalRequestDetails.class);
                intent.putExtra("hname",model.getHName());
                v.getContext().startActivity(intent);
            }
        });

    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        TextView name,email,phone,address;
        CircleImageView img;
        public myviewholder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            email=(TextView)itemView.findViewById(R.id.emailtext);
            phone=(TextView)itemView.findViewById(R.id.phonetext);
            address=(TextView)itemView.findViewById(R.id.addresstext);
        }
    }


}