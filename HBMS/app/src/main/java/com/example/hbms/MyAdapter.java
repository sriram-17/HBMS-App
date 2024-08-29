package com.example.hbms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends FirebaseRecyclerAdapter<Model,MyAdapter.myviewholder> {
    Context context;
    List<Model> list;
    String userID;

       public MyAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Model> options, Context context, List<Model> list,String userID) {
        super(options);
        this.context = context;
        this.list = list;
        this.userID=userID;
    }

    public MyAdapter(FirebaseRecyclerOptions<Model> options, Context applicationContext) {
        super(options);
    }
    public MyAdapter(FirebaseRecyclerOptions<Model> options, Context applicationContext, String userID) {
        super(options);
        this.userID=userID;
    }


    @Override
    protected void onBindViewHolder(@NonNull @NotNull MyAdapter.myviewholder holder, int position, @NonNull @NotNull Model model) {
           //Model item=list.get(position);
           holder.name.setText(model.getHname());
        holder.email.setText(model.getEmail());
        holder.phone.setText(model.getPhone());
        holder.address.setText(model.getAddress());
        Glide.with(holder.img.getContext()).load(model.getImage()).into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),dashboard.class);
                intent.putExtra("hname",model.getHname());
                intent.putExtra("userID",userID);
                v.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @NotNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }



    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView name,email,phone,address;
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
