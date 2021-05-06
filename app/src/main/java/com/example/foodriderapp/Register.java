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

public class Register extends AppCompatActivity {

    EditText RfullName, REmail, RPhone, RPassword;
    Button Rbutton;
    TextView RloginNav;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RfullName = findViewById(R.id.Name);
        REmail = findViewById(R.id.Email);
        RPhone = findViewById(R.id.phoneNumber);
        RPassword = findViewById(R.id.Password);
        Rbutton = findViewById(R.id.regButton);
        RloginNav = findViewById(R.id.loginDirect);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        Rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = REmail.getText().toString().trim();
                String Password = RPassword.getText().toString().trim();

                if (TextUtils.isEmpty(Email))
                {
                    REmail.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(Password))
                {
                    RPassword.setError("Password is required!");
                    return;
                }
                if(Password.length() < 6)
                {
                    RPassword.setError("Password must be 6 character long!");
                    return;
                }
                //Register User
                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this, "User Created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        RloginNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}