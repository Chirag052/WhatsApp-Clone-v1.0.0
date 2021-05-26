package com.example.whatsappclonev100;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.whatsappclonev100.Adapter.ChatAdapter;
import com.example.whatsappclonev100.Model.InfoModel;
import com.example.whatsappclonev100.Model.MessageModel;
import com.example.whatsappclonev100.Model.Users;
import com.example.whatsappclonev100.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding activityChatDetailBinding;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ArrayList<Users> users=new ArrayList<>();
    InfoModel senderModel = new InfoModel();
    InfoModel recieverModel = new InfoModel();
    boolean isMessaged=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChatDetailBinding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(activityChatDetailBinding.getRoot());

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final String senderId = auth.getUid();
        String recieverId  = getIntent().getStringExtra("userId");
        String username  = getIntent().getStringExtra("userName");
        String profilepic  = getIntent().getStringExtra("profilePic");
        String  mobileNo = getIntent().getStringExtra("phoneNo");
        final String senderRoom = senderId + recieverId;
        final String recieverRoom = recieverId+senderId;

        activityChatDetailBinding.username.setText(username);
        Picasso.get().load(profilepic).placeholder(R.drawable.man).into(activityChatDetailBinding.profileImage);




        activityChatDetailBinding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetailActivity.this, MainActivity.class);

                startActivity(intent);

            }
        });

        final ArrayList<MessageModel> messageModels = new ArrayList<>();

        final ChatAdapter chatAdapter = new ChatAdapter(messageModels, this, recieverId,profilepic,username);
        activityChatDetailBinding.messagesrecycler.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityChatDetailBinding.messagesrecycler.setLayoutManager(layoutManager);
        firestore.collection("Chats").document(senderRoom).collection("Messages").orderBy("timeStamp", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                messageModels.clear();
                for(DocumentSnapshot snapshot:value.getDocuments()){
                    MessageModel messageModel = snapshot.toObject(MessageModel.class);
                    messageModel.setMessageId(snapshot.getId());
                    messageModels.add(messageModel);
                }
                chatAdapter.notifyDataSetChanged();
                activityChatDetailBinding.messagesrecycler.smoothScrollToPosition(activityChatDetailBinding.messagesrecycler.getAdapter().getItemCount());
            }
        });



        activityChatDetailBinding.sendmssg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (activityChatDetailBinding.messagesbyuser.getText().toString().trim().equalsIgnoreCase("")) {
                    activityChatDetailBinding.messagesbyuser.setError("This field can not be blank");
                }else{
                    String message =
                            activityChatDetailBinding.messagesbyuser.getText().toString();


                    final MessageModel messageModel = new MessageModel(senderId, message);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
                    String datetime = simpleDateFormat.format(calendar.getTime());

                    messageModel.setTimeStamp(new Date().getTime());
                    messageModel.setTime(datetime);

                activityChatDetailBinding.messagesbyuser.setText("");
                senderModel.setProfilePic(profilepic);
                senderModel.setProfileName(username);
                senderModel.setRecieverId(recieverId);
                senderModel.setSenderId(senderId);


                if(senderModel.getMobileNo()==null ){
                    senderModel.setMobileNo(mobileNo);

                }
                if(senderModel.getSenderMobileNo()==null){
                    senderModel.setSenderMobileNo(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

                }
                senderModel.setRecieverNo(mobileNo);





                firestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
                        for(DocumentSnapshot snapshot:value.getDocuments()){
                            if(snapshot.getId().equals(senderId)){
                                Users allusers = snapshot.toObject(Users.class);
                                recieverModel.setProfileName(allusers.getUserName());
                                recieverModel.setProfilePic(allusers.getProfilepic());
                                recieverModel.setMobileNo(senderModel.getSenderMobileNo());
                                recieverModel.setSenderMobileNo(senderModel.getMobileNo());
                                recieverModel.setSenderId(senderModel.getRecieverId());
                                recieverModel.setRecieverId(senderModel.getSenderId());


                            }
                        }
                    }
                });
                firestore.collection("Chats").document(senderRoom).set(senderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        firestore.collection("Chats").document(recieverRoom).set(recieverModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });


                DocumentReference documentReference = firestore.collection("Chats").document(auth.getUid()).collection("Messages").document();
                String noteId =documentReference.getId();


                firestore.collection("Chats").document(senderRoom).collection("Messages").document(noteId).set(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        firestore.collection("Chats").document(recieverRoom).collection("Messages").document(noteId).set(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });



            }}
        });

    }
}