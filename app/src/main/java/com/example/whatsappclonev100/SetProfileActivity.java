package com.example.whatsappclonev100;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappclonev100.Model.Users;
import com.example.whatsappclonev100.databinding.ActivityChatDetailBinding;
import com.example.whatsappclonev100.databinding.ActivitySetProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SetProfileActivity extends AppCompatActivity {

    EditText usernameedittext,aboutusedittext;
    Button save;
    ImageView addbutton,profilePic;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ActivitySetProfileBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        binding = ActivitySetProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profilePic=findViewById(R.id.profile_pic);
        firestore=FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            firestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Users users = documentSnapshot.toObject(Users.class);
                    Picasso.get()
                            .load(users.getProfilepic()).placeholder(R.drawable.man).into(profilePic);
                    binding.aboutusedittext.setText(users.getStatus());
                    binding.usernametext.setText(users.getUserName());

                }
            });
        }


        binding.profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 33);

            }
        });


        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = binding.aboutusedittext.getText().toString();
                String username = binding.usernametext.getText().toString();
                if (binding.usernametext.getText().toString().trim().equalsIgnoreCase("")) {
                    binding.usernametext.setError("This field can not be blank");
                }else{
                    DocumentReference db = firestore.collection("Users").document(auth.getUid());

                    HashMap<String, Object> obj= new HashMap<>();
                    obj.put("userName", username);
                    obj.put("status", status);

                    db.update(obj);
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null)
        {
            Uri sFIle  = data.getData();
            binding.profilePic.setImageURI(sFIle);

            final StorageReference reference= storage.getReference().child("profile pic").child(FirebaseAuth.getInstance().getUid());


            reference.putFile(sFIle).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DocumentReference db = firestore.collection("Users").document(auth.getUid());
                            HashMap<String, Object> obj= new HashMap<>();
                            obj.put("profilepic", uri.toString());
                            db.update(obj);
                        }
                    });
                }
            });
        }
    }
}