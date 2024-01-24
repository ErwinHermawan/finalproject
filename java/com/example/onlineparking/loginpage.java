package com.example.onlineparking;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginpage extends AppCompatActivity {
    Button Login;
    TextInputEditText mname, mpassword, memail;
    FirebaseFirestore firestore;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), landingpage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        Login = findViewById(R.id.button);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mname = findViewById(R.id.name);
        mpassword = findViewById(R.id.password);
        memail = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar);
        Button forgotPassword = findViewById(R.id.forgot_password);


                Login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        String name, password, email;
                        name = String.valueOf(mname.getText());
                        password = String.valueOf(mpassword.getText());
                        email = String.valueOf(memail.getText());

                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(loginpage.this, "Enter name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (TextUtils.isEmpty(password)) {
                            Toast.makeText(loginpage.this, "Enter password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (TextUtils.isEmpty(email)) {
                            Toast.makeText(loginpage.this, "Enter email", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {

                                            Toast.makeText(loginpage.this, "Login Success",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), landingpage.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(loginpage.this, "Login failed",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the forgot password method
                forgotPassword();
            }

            private void forgotPassword() {
                String email = memail.getText().toString().trim();

                // Check if the email address is valid
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(loginpage.this, "Please enter your email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(loginpage.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(loginpage.this, "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}