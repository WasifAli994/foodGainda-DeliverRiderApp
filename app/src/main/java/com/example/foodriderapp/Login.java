package com.example.foodriderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText LEmail,LPassword;
    Button LButton;
    TextView LRegisternav;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LEmail = findViewById(R.id.loginEmail);
        LPassword = findViewById(R.id.loginPassword);
        LButton = findViewById(R.id.loginButton);
        fAuth = FirebaseAuth.getInstance();
        LRegisternav = findViewById(R.id.createAccountNav);

        LButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = LEmail.getText().toString().trim();
                String Password = LPassword.getText().toString().trim();

                if (TextUtils.isEmpty(Email))
                {
                    LEmail.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(Password))
                {
                    LPassword.setError("Password is required!");
                    return;
                }
                if(Password.length() < 6)
                {
                    LPassword.setError("Password must be 6 character long!");
                    return;
                }

                //Authenticate User:

                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "Logged in Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        LRegisternav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}