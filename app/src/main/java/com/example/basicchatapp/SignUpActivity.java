package com.example.basicchatapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicchatapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        color();

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        button();



    }



    private void button() {
        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth();
            }
        });

        binding.signUpInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });

    }

    private void auth() {
        mAuth.createUserWithEmailAndPassword(binding.signInEmail.getText().toString(),binding.signInPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                        } else {

                            Toast.makeText(SignUpActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }
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
        binding.signUpButton.setTextColor(getResources().getColor(R.color.white));
        binding.signUpInButton.setTextColor(getResources().getColor(R.color.white));
        binding.signUpButton.setBackgroundColor(getResources().getColor(R.color.black));
        binding.signUpInButton.setBackgroundColor(getResources().getColor(R.color.black));

    }

    private void nightModeOn() {
        binding.signUpButton.setTextColor(getResources().getColor(R.color.black));
        binding.signUpInButton.setTextColor(getResources().getColor(R.color.black));
        binding.signUpButton.setBackgroundColor(getResources().getColor(R.color.white));
        binding.signUpInButton.setBackgroundColor(getResources().getColor(R.color.white));

    }

}

