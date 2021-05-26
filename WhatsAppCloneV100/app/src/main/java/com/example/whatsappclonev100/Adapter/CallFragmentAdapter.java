package com.example.whatsappclonev100.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclonev100.Model.CallsModel;
import com.example.whatsappclonev100.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CallFragmentAdapter  extends RecyclerView.Adapter<CallFragmentAdapter.CallViewHolder> {
    Context ctx;
    ArrayList<CallsModel> calls;

    public CallFragmentAdapter(Context ctx, ArrayList<CallsModel> calls) {
        this.ctx = ctx;
        this.calls = calls;
    }

    @NonNull
    @NotNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.call_layout, parent, false);
        return new CallFragmentAdapter.CallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CallFragmentAdapter.CallViewHolder holder, int position) {
        holder.name.setText(calls.get(position).getName());
        holder.callfrom.setText(calls.get(position).getCallfrom());
        if(calls.get(position).getIncoming()=="incoming"){
            holder.incoming.setImageResource(R.drawable.incoming_call);
        }else if(calls.get(position).getIncoming()=="outgoing"){
            holder.incoming.setImageResource(R.drawable.outgoing);
        }
        if(calls.get(position).getVideocall()=="call"){
            holder.call.setImageResource(R.drawable.ic_baseline_call_24);
        }else if(calls.get(position).getVideocall()=="videocall"){
            holder.call.setImageResource(R.drawable.ic_baseline_videocam_24);
        }

        holder.profilePic.setImageResource(calls.get(position).getProfilepic());

    }

    @Override
    public int getItemCount() {
        return calls.size();
    }

    public class CallViewHolder extends RecyclerView.ViewHolder {

        TextView name,callfrom;
        ImageView incoming,profilePic,call;

        public CallViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_user);
            callfrom=itemView.findViewById(R.id.call_from);
            profilePic=itemView.findViewById(R.id.profile_image);
            incoming=itemView.findViewById(R.id.incoming);
            call=itemView.findViewById(R.id.videocall);
        }
    }
}
