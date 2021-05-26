package com.example.whatsappclonev100.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsappclonev100.Model.StatusModel;
import com.example.whatsappclonev100.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
    Context ctx;
    ArrayList<StatusModel> statusModel;

    public StatusAdapter(Context ctx, ArrayList<StatusModel> statusModel) {
        this.ctx = ctx;
        this.statusModel = statusModel;
    }

    @NotNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.status_layout, parent, false);
        return new StatusAdapter.StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StatusAdapter.StatusViewHolder holder, int position) {
        holder.name.setText(statusModel.get(position).getName());
        holder.profilepic.setImageResource(statusModel.get(position).getProfilePic());
        holder.time.setText(statusModel.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return statusModel.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {

        TextView name,time;
        ImageView profilepic;
        public StatusViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name_user);
            time=view.findViewById(R.id.time);
            profilepic=view.findViewById(R.id.profile_image);
        }
    }
}
