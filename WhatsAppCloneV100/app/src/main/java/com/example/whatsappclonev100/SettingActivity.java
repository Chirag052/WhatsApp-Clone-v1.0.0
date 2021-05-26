package com.example.whatsappclonev100;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappclonev100.Model.Users;
import com.example.whatsappclonev100.databinding.ActivitySettingBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class SettingActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    ImageView backbtn;
    LinearLayout linearLayout;
    ActivitySettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        backbtn=findViewById(R.id.backarrowsetting);
        linearLayout=(LinearLayout)findViewById(R.id.opensetdp);
        firestore = FirebaseFirestore.getInstance();
        binding=ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users=documentSnapshot.toObject(Users.class);
                Picasso.get()
                        .load(users.getProfilepic()).placeholder(R.drawable.man).into(binding.profileImage);
                binding.status.setText(users.getStatus());
                binding.nameUser.setText(users.getUserName());

            }
        });

        binding.opensetdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SetdpFromSettingsActivity.class);
                startActivity(intent);
            }
        });



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });

    }
}