package com.example.whatsappclonev100.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclonev100.Model.InfoModel;
import com.example.whatsappclonev100.Model.MessageModel;
import com.example.whatsappclonev100.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter  {


    ArrayList<MessageModel> messageModels;
    Context context;
    String recID,profilePic,userName;
    InfoModel infoModel=new InfoModel();


    int SENDER_VIEW_TYPE = 1;
    int RECIEVER_VIEW_TYPE = 2;
    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recID, String profilepic, String username) {
        this.messageModels = messageModels;
        this.context = context;
        this.recID = recID;
        this.profilePic=profilepic;
        this.userName = username;

    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.message_sender,parent,false);
            return new SenderViewHoder(view);
        }
        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.message_reciever,parent,false);
            return new RecieverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = messageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete message?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                String senderRoom =FirebaseAuth.getInstance().getUid() + recID;
//                                database.getReference().child("Chats").child(senderRoom).child(messageModel.getMessageId())
//                                        .setValue(null);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                    }
                }).show();

                return false;
            }
        });
        if(holder.getClass() == SenderViewHoder.class)
        {
            ((SenderViewHoder)holder).sendermsg.setText(messageModel.getMessage());
            ((SenderViewHoder)holder).sendertime.setText(messageModel.getTime());

        }
        else
        {
            ((RecieverViewHolder)holder).recievermsg.setText(messageModel.getMessage());
            ((RecieverViewHolder)holder).recievertime.setText(messageModel.getTime());

        }

    }

    @Override
    public int getItemViewType(int position) {

        if(messageModels.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECIEVER_VIEW_TYPE;
        }


    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }



    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView recievermsg, recievertime;

        public RecieverViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            recievermsg = itemView.findViewById(R.id.recievertext);
            recievertime = itemView.findViewById(R.id.recievertime);
        }
    }

    public class SenderViewHoder extends RecyclerView.ViewHolder{

        TextView sendermsg, sendertime;

        public SenderViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            sendermsg = itemView.findViewById(R.id.sendertext);
            sendertime = itemView.findViewById(R.id.sendertime);
        }
    }


}
