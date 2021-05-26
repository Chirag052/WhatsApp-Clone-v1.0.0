package com.example.whatsappclonev100;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappclonev100.Model.Users;
import com.example.whatsappclonev100.databinding.ActivitySetdpFromSettingsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SetdpFromSettingsActivity extends AppCompatActivity {

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ActivitySetdpFromSettingsBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setdp_from_settings);
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        binding = ActivitySetdpFromSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firestore = FirebaseFirestore.getInstance();
        binding.backarrowsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        firestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users=documentSnapshot.toObject(Users.class);
                Picasso.get()
                        .load(users.getProfilepic()).placeholder(R.drawable.man).into(binding.profilePic);
                binding.aboutTv.setText(users.getStatus());
                binding.usernameTv.setText(users.getUserName());

            }
        });

        binding.llEditname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetEditName();
            }
        });
        binding.llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetAbout();
            }
        });


        binding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 33);

            }
        });
        firestore.collection("Users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users user = documentSnapshot.toObject(Users.class);
                binding.mobileTv.setText(user.getUserMobileNo());
            }
        });

    }

    private void showBottomSheetAbout() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                SetdpFromSettingsActivity.this, R.style.BottomSheetDialogTheme
        );


        View sheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.about_bottom_sheet,
                        (ViewGroup) findViewById(R.id.aboutBottomSheet)
                );

        DocumentReference db = firestore.collection("Users").document(auth.getUid());


        EditText et = sheetView.findViewById(R.id.about_et);
        firestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users=documentSnapshot.toObject(Users.class);
                et.setText(users.getStatus());

            }
        });
        sheetView.findViewById(R.id.save_abt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et.getText().toString().trim().equalsIgnoreCase("")) {
                    et.setError("This field can not be blank");
                }else{
                HashMap<String, Object> obj= new HashMap<>();
                obj.put("status",et.getText().toString());
                db.update(obj);
                Toast.makeText(SetdpFromSettingsActivity.this, "Status Updated", Toast.LENGTH_SHORT).show();
            }}
        });
        sheetView.findViewById(R.id.cancekl_abt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();

    }

    private void showBottomSheetEditName() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                SetdpFromSettingsActivity.this, R.style.BottomSheetDialogTheme
        );


        View sheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.bottom_sheet,
                        (ViewGroup) findViewById(R.id.bottomSheetConatiner)
                );

        DocumentReference db = firestore.collection("Users").document(auth.getUid());


        EditText et = sheetView.findViewById(R.id.usernameedittext);
        firestore.collection("Users").document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users=documentSnapshot.toObject(Users.class);
                et.setText(users.getUserName());


            }
        });
        sheetView.findViewById(R.id.save_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et.getText().toString().trim().equalsIgnoreCase("")) {
                    et.setError("Name can't be empty!");
                }else{
                HashMap<String, Object> obj= new HashMap<>();
                obj.put("userName",et.getText().toString());
                db.update(obj);
                Toast.makeText(SetdpFromSettingsActivity.this, "Profile Name Updated", Toast.LENGTH_SHORT).show();
            }}
        });
        sheetView.findViewById(R.id.cancel_bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();

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