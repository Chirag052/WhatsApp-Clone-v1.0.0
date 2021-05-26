package com.example.whatsappclonev100.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.whatsappclonev100.Adapter.CallFragmentAdapter;
import com.example.whatsappclonev100.Adapter.StatusAdapter;
import com.example.whatsappclonev100.Model.CallsModel;
import com.example.whatsappclonev100.Model.StatusModel;
import com.example.whatsappclonev100.Model.Users;
import com.example.whatsappclonev100.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StatusFragment extends Fragment {


    public StatusFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    StatusAdapter statusAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_status, container, false);

        recyclerView=view.findViewById(R.id.status_recycler);
        ImageView profilepic = view.findViewById(R.id.profile_image);
        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users=documentSnapshot.toObject(Users.class);
                Picasso.get()
                        .load(users.getProfilepic()).placeholder(R.drawable.man).into(profilepic);

            }
        });
        ArrayList<StatusModel> statusModelArrayList = new ArrayList<>();


            statusModelArrayList.add( new StatusModel("Ranbir Kp", "Yesterday, 11:46 pm", R.drawable.ranbir));
            statusModelArrayList.add( new StatusModel("Alia Bt", "38 minutes ago", R.drawable.alia));
            statusModelArrayList.add( new StatusModel("Govind", "Yesterday, 12:50 pm", R.drawable.hrithik));
            statusModelArrayList.add( new StatusModel("bunny", "59 minutes ago", R.drawable.allu));
            statusModelArrayList.add( new StatusModel("sammy", " Yesterday, 9:54 pm", R.drawable.samantha));
            statusModelArrayList.add( new StatusModel("Madhav", "Today, 12:41 am", R.drawable.prabhas));
            statusModelArrayList.add( new StatusModel("Veer", "Today, 12:41 am", R.drawable.vikrant));


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        statusAdapter = new StatusAdapter(getContext(),statusModelArrayList);
        recyclerView.setAdapter(statusAdapter);

        return view;

    }
}