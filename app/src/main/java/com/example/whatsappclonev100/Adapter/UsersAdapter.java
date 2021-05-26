package com.example.whatsappclonev100.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclonev100.ChatDetailActivity;
import com.example.whatsappclonev100.Model.InfoModel;
import com.example.whatsappclonev100.Model.MessageModel;
import com.example.whatsappclonev100.Model.PhoneUsers;
import com.example.whatsappclonev100.R;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    ArrayList<InfoModel> users;
    Context context;
    boolean myflag;
    public UsersAdapter(ArrayList<InfoModel> users, Context context,boolean flag) {
        this.users = users;
        this.context = context;
        myflag= flag;
        Log.i("myflag", String.valueOf(myflag));

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Log.i("inadapter", String.valueOf(myflag));
        holder.user_name.setText(users.get(position).getProfileName());
        Picasso.get().load(users.get(position).getProfilePic()).placeholder(R.drawable.man).into(holder.profile_img);
        FirebaseFirestore.getInstance().collection("Chats")
                .document(FirebaseAuth.getInstance().getUid() + users.get(position).getRecieverId())
                .collection("Messages").orderBy("timeStamp").limitToLast(1).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                if(!value.isEmpty()){
                    for(DocumentSnapshot documentSnapshot:value.getDocuments()){
                        holder.last_message.setVisibility(View.VISIBLE);
                        holder.last_message.setText(documentSnapshot.toObject(MessageModel.class).getMessage());

                    }
                }
            }

        });
        if(users.get(position).isInContact()){
            holder.notincontactchip.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userId", users.get(position).getRecieverId());
                intent.putExtra("profilePic", users.get(position).getProfilePic());
                intent.putExtra("userName", users.get(position).getProfileName());
                intent.putExtra("phoneNo",users.get(position).getMobileNo());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_name, last_message;
        CircleImageView profile_img;
        TextView notincontactchip;

        public ViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);

            user_name = itemView.findViewById(R.id.name_user);
            last_message = itemView.findViewById(R.id.last_mssg_user);
            profile_img = itemView.findViewById(R.id.profile_image);
            notincontactchip=itemView.findViewById(R.id.not_in_contact_chip);
        }
    }
}
