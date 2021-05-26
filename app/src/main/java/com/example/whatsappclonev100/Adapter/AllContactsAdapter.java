package com.example.whatsappclonev100.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclonev100.ChatDetailActivity;
import com.example.whatsappclonev100.Model.PhoneUsers;
import com.example.whatsappclonev100.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.AllContactsHolder> {

    Context context;
    ArrayList<PhoneUsers> allContacts;

    public AllContactsAdapter(Context context, ArrayList<PhoneUsers> allContacts) {
        this.context = context;
        this.allContacts = allContacts;
    }

    @NonNull
    @Override
    public AllContactsHolder onCreateViewHolder(@NonNull  ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
       View view = layoutInflater.inflate(R.layout.allcontactsprofile,viewGroup,false);
       return new AllContactsHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllContactsHolder holder, int position) {
        holder.user_name.setText(allContacts.get(position).getUserName());
        holder.status.setVisibility(View.VISIBLE);
        holder.status.setText(allContacts.get(position).getStatus());
        Picasso.get().load(allContacts.get(position).getProfilepic()).placeholder(R.drawable.man).into(holder.profile_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userId", allContacts.get(position).getUserId());
                intent.putExtra("profilePic", allContacts.get(position).getProfilepic());
                intent.putExtra("userName", allContacts.get(position).getUserName());
                intent.putExtra("phoneNo",allContacts.get(position).getUserMobileNo());

                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return allContacts.size();
    }

    public class AllContactsHolder extends RecyclerView.ViewHolder {
        TextView user_name,status;
        ImageView profile_img;
        public AllContactsHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.name_user);
            status = itemView.findViewById(R.id.status);
            profile_img = itemView.findViewById(R.id.profile_image);
        }
    }
}
