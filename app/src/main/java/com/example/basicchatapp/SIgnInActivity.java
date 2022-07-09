package com.example.basicchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicchatapp.databinding.ActivitySigninBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SIgnInActivity extends AppCompatActivity {

    ActivitySigninBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        button();

        mAuth = FirebaseAuth.getInstance();

    }

    private void button() {
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth(binding.signInEmail.getText().toString(), binding.signInPassword.getText().toString());
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SIgnInActivity.this, SignUpActivity.class));
            }
        });
    }

    private void auth(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(SIgnInActivity.this, MainActivity.class));

                        } else {

                            Toast.makeText(SIgnInActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}