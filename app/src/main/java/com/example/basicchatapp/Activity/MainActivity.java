package com.example.basicchatapp.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.basicchatapp.Adapter.MsgAdapter;
import com.example.basicchatapp.Model.User;
import com.example.basicchatapp.R;
import com.example.basicchatapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    ArrayList<User> users;
    DatabaseReference myRef;
    FirebaseDatabase database;
    FirebaseAuth mauth;
    MsgAdapter msgAdapter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        users = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        mauth = FirebaseAuth.getInstance();
        myRef = database.getReference();


        readData();
        userList();
        users();

        colors();

    }

    private void colors() {

        int nightModeFlags =
                MainActivity.this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                night();
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                day();
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }
    }

    private void day() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.black)));

    }

    private void night() {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
    }

    private void readData() {
        // Read from the database
        myRef.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               users.clear();
               for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                   user = new User();
                   user = snapshot.getValue(User.class);

                  if (mauth.getCurrentUser().getEmail() != user.getEmail()){

                      users.add(user);
                  }


               }
               msgAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Shajidur", "Failed to read value.", error.toException());
            }
        });
    }

    // on item clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOutmenu:
                logout();
                break;
            case R.id.settings:
                Toast.makeText(this, "Settings will be available soon", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // Firebase sign out
    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this, SIgnInActivity.class));
        finish();
    }

    // menu init
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.upper, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private ArrayList<User> users() {
        return users;
    }

    private void userList() {
        binding.userList.setLayoutManager(new LinearLayoutManager(this));
        msgAdapter = new MsgAdapter(this, users());
        binding.userList.setAdapter(msgAdapter);
    }

    // Read from the database



}