package com.example.basicchatapp.Activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicchatapp.Adapter.UserMessageAdapter;
import com.example.basicchatapp.Model.Message;
import com.example.basicchatapp.R;
import com.example.basicchatapp.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    ArrayList<Message> messageArrayList;
    UserMessageAdapter userMessageAdapter;
    String receiverRoom;
    String senderRoom;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();

        senderRoom = null;
        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String receiverUid = intent.getStringExtra("uid");

        senderRoom = receiverUid + senderUid;
        receiverRoom  = senderUid+ receiverUid;

        messageArrayList = new ArrayList<>();

        userMessageAdapter = new UserMessageAdapter(this, messageArrayList);

        getSupportActionBar().setTitle(name);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        binding.chatRecyclerView.scrollToPosition(0); //
        binding.chatRecyclerView.setLayoutManager(linearLayoutManager);
        binding.chatRecyclerView.setAdapter(userMessageAdapter);



        myRef.child("Chats").child(senderRoom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messageArrayList.clear();

                for (DataSnapshot snap: snapshot.getChildren()){
                    Message message = new Message();
                    message = snap.getValue(Message.class);
                    Log.d("ShajidurRahman", "onDataChange: " + message.getSenderId() + " " + message.getMessage());
                    messageArrayList.add(message);
                }
                userMessageAdapter.notifyDataSetChanged();
                binding.chatRecyclerView.smoothScrollToPosition(binding.chatRecyclerView.getAdapter().getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.chatmsg.getText().toString();
                Message messageobject = new Message(message,senderUid);
                binding.chatmsg.setText("");
                myRef.child("Chats").child(senderRoom).child("messages").push()
                        .setValue(messageobject).addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void unused) {
                                myRef.child("Chats").child(receiverRoom).child("messages").push()
                                        .setValue(messageobject);
                            }
                        });

                userMessageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onItemRangeInserted(int positionStart, int itemCount) {
                        linearLayoutManager.smoothScrollToPosition(binding.chatRecyclerView, null, userMessageAdapter.getItemCount());
                    }
                });

            }
        });

    }
    private void setListeners() {
        binding.chatRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom)
                    if (messageArrayList.size()==0){
                        binding.chatRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                binding.chatRecyclerView.smoothScrollToPosition(messageArrayList.size());
                            }
                        }, 100);
                    }else{
                        binding.chatRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                binding.chatRecyclerView.smoothScrollToPosition(messageArrayList.size()-1);
                            }
                        }, 100);
                    }
            }
        });

    }
}