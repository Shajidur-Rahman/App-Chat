package com.example.basicchatapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicchatapp.Model.Message;
import com.example.basicchatapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<Message> messageArrayList;

    public UserMessageAdapter(Context context, ArrayList<Message> messageArrayList) {
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sent, parent, false);
            return new SentViewHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recieve, parent, false);
            return new RecieveViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Message message = (Message) messageArrayList.get(position);
//
        if (holder.getClass() == SentViewHolder.class){
            Message message1 = messageArrayList.get(position);
            SentViewHolder sentViewHolder = (SentViewHolder) holder;
            ((SentViewHolder) holder).message.setText(message1.getMessage());
            Log.d("ShajidurRahman", "onBindViewHolder: " + messageArrayList.size());
        } else {
            Message message = messageArrayList.get(position);
            RecieveViewHolder recieveViewHolder = (RecieveViewHolder) holder;
            ((RecieveViewHolder) holder).message.setText(message.getMessage());
        }
//
//        switch (holder.getItemViewType()) {
//            case VIEW_TYPE_MESSAGE_SENT:
//                ((SentViewHolder) holder).bind(message);
//                break;
//            case VIEW_TYPE_MESSAGE_RECEIVED:
//                ((RecieveViewHolder) holder).bind(message);
//        }
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message message = messageArrayList.get(position);

        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(message.getSenderId())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    public class SentViewHolder extends RecyclerView.ViewHolder{

        TextView message;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.sentText);

        }

    }

    public class RecieveViewHolder extends RecyclerView.ViewHolder{

        TextView message;

        public RecieveViewHolder(@NonNull View itemView) {
            super(itemView);

            message = itemView.findViewById(R.id.receiveText);

        }


    }
}
