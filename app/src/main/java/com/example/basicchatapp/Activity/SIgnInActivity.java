package com.example.basicchatapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicchatapp.MainActivity;
import com.example.basicchatapp.R;
import com.example.basicchatapp.databinding.ActivitySigninBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        startActivity(new Intent(SIgnInActivity.this, MainActivity.class));
    }
    private void button() {
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.signInEmail.getText().toString();
                String password = binding.signInPassword.getText().toString();

                if (email.length() == 0 && password.length() == 0){
                    binding.signInEmail.setError("Email require");
                    binding.signInPassword.setError("Password require");
                } else if (email.length() < 16 ){
                    binding.signInEmail.setError("Email require");
                } else if(password.length() < 8){
                    binding.signInPassword.setError("Password require");
                }
                else {
                    auth(binding.signInEmail.getText().toString(), binding.signInPassword.getText().toString());
                }

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
                            finish();

                        } else {

                            Toast.makeText(SIgnInActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    private void color() {
        int nightModeFlags =
                SIgnInActivity.this.getResources().getConfiguration().uiMode &
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

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void lightModeOn() {
        binding.button.setTextColor(getResources().getColor(R.color.black));
        binding.button2.setTextColor(getResources().getColor(R.color.black));
        binding.button.setBackgroundColor(getResources().getColor(R.color.white));
        binding.button2.setBackgroundColor(getResources().getColor(R.color.white));

    }

    private void nightModeOn() {
        binding.button.setTextColor(getResources().getColor(R.color.black));
        binding.button2.setTextColor(getResources().getColor(R.color.black));
        binding.button.setBackgroundColor(getResources().getColor(R.color.white));
        binding.button2.setBackgroundColor(getResources().getColor(R.color.white));

    }

}