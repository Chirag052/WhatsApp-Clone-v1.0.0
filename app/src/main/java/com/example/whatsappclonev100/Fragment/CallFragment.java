package com.example.whatsappclonev100.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappclonev100.Adapter.AllContactsAdapter;
import com.example.whatsappclonev100.Adapter.CallFragmentAdapter;
import com.example.whatsappclonev100.Model.CallsModel;
import com.example.whatsappclonev100.R;
import com.example.whatsappclonev100.databinding.CallLayoutBinding;

import java.util.ArrayList;

public class CallFragment extends Fragment {


    public CallFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    CallFragmentAdapter callFragmentAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_call, container, false);

        recyclerView=view.findViewById(R.id.callrecycler);

        ArrayList<CallsModel> calls = new ArrayList<>();
        calls.add(new CallsModel("Veer", R.drawable.vikrant, "incoming", "Yesterday, 6:46 pm","call"));
        calls.add(new CallsModel("Ranbir Kp", R.drawable.ranbir, "outgoing", "18 May, 8:13 pm","videocall"));
        calls.add(new CallsModel("Ritesh", R.drawable.man, "outgoing", "15 May, 12:13 pm","call"));
        calls.add(new CallsModel("Alia Bt", R.drawable.alia, "outgoing", "7 May, 7:43 pm","videocall"));
        calls.add(new CallsModel("Madhav", R.drawable.prabhas, "incoming", "29 April, 8:13 pm","call"));
        calls.add(new CallsModel("Aksh", R.drawable.profile, "outgoing", "20 April, 10:50 pm","videocall"));
        calls.add(new CallsModel("bunny", R.drawable.allu, "incoming", "20 April, 2:13 am","videocall"));
        calls.add(new CallsModel("Shyam", R.drawable.man, "outgoing", "10 April, 5:52 pm","call"));
        calls.add(new CallsModel("Rahul", R.drawable.hrithik, "incoming", "20 March, 1:01 am","videocall"));
        calls.add(new CallsModel("sammy", R.drawable.samantha, "outgoing", "5 March, 8:138 pm","call"));


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        callFragmentAdapter = new CallFragmentAdapter(getContext(),calls);
        recyclerView.setAdapter(callFragmentAdapter);


        return  view;
    }
}