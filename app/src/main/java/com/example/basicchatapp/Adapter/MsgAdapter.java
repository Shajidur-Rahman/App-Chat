package com.example.basicchatapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicchatapp.Model.User;
import com.example.basicchatapp.R;

import java.util.ArrayList;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MsgAdapterViewHolder> {

    Context context;
    ArrayList<User> userArrayList;

    public MsgAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MsgAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);

        return new MsgAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapterViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.name.setText(user.getName());
        holder.msg.setText(user.getLastMsg());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class MsgAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView msg;

        public MsgAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.personName);
            msg = itemView.findViewById(R.id.lastMsg);

        }
    }
}
