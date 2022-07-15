package com.example.basicchatapp.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicchatapp.MainActivity;
import com.example.basicchatapp.Model.User;
import com.example.basicchatapp.R;
import com.example.basicchatapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        color();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().hide();

        button();



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }
    }

    private void reload() {
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
    }

    private void button() {
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signUpEmail.getText().toString();
                String password = binding.signUpPassword.getText().toString();

                if (email.length() < 16 && password.length() < 8){
                    binding.signUpEmail.setError("Email must be 15 chr");
                    binding.signUpPassword.setError("Password must be 8 chr");
                } else if (email.length() < 16 ){
                    binding.signUpEmail.setError("Email must be 15 chr");
                } else if(password.length() < 8){
                    binding.signUpPassword.setError("Password must be 8 chr");
                } else {
                    auth();
                }
            }
        });

        binding.signUpInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, SIgnInActivity.class));
            }
        });

    }

    private void auth() {
        mAuth.createUserWithEmailAndPassword(binding.signUpEmail.getText().toString(),binding.signUpPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userToDataBase(binding.signUpName.getText().toString(), binding.signUpEmail.getText().toString(), binding.signUpPassword.getText().toString(), mAuth.getUid());
                            startActivity(new Intent(SignUpActivity.this, SIgnInActivity.class));

                        } else {

                            Toast.makeText(SignUpActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    private void userToDataBase(String name, String email, String password, String uid) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        User user = new User(name, email, password, uid);
        myRef.child(uid).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SignUpActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void color() {
        int nightModeFlags =
                SignUpActivity.this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                nightModeOn();
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                lightModeOn();
                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                break;
        }




    }

    private void lightModeOn() {
        binding.signUpButton.setTextColor(getResources().getColor(R.color.black));
        binding.signUpInButton.setTextColor(getResources().getColor(R.color.black));
        binding.signUpButton.setBackgroundColor(getResources().getColor(R.color.white));
        binding.signUpInButton.setBackgroundColor(getResources().getColor(R.color.white));

    }

    private void nightModeOn() {
        binding.signUpButton.setTextColor(getResources().getColor(R.color.black));
        binding.signUpInButton.setTextColor(getResources().getColor(R.color.black));
        binding.signUpButton.setBackgroundColor(getResources().getColor(R.color.white));
        binding.signUpInButton.setBackgroundColor(getResources().getColor(R.color.white));

    }

}

