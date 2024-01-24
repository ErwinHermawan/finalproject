package com.example.onlineparking;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class register extends AppCompatActivity {
    Button button;
    TextInputEditText mname, mpassword, memail, mconfirmpassword;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

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
        setContentView(R.layout.activity_register);
        button = findViewById(R.id.button);
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mname = findViewById(R.id.name);
        mpassword = findViewById(R.id.password);
        memail = findViewById(R.id.email);
        mconfirmpassword = findViewById(R.id.confirm_password);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), loginpage.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name, password, email, confirmPassword;
                name = String.valueOf(mname.getText());
                password = String.valueOf(mpassword.getText());
                email = String.valueOf(memail.getText());
                confirmPassword = String.valueOf(mconfirmpassword.getText());

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(register.this, "Enter name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(register.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(register.this, "Passwords does not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)

                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    sendEmailVerification();
                                    /* Toast.makeText(register.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), landingpage.class);
                                    startActivity(intent);
                                    finish();

                                     */
                                } else {
                                    Toast.makeText(register.this, "Registration failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                            private void sendEmailVerification() {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressBar.setVisibility(View.GONE);
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(register.this, "Registration successful. Please check your email for verification.",
                                                                Toast.LENGTH_SHORT).show();
                                                        mAuth.signOut();
                                                        Intent intent = new Intent(getApplicationContext(), loginpage.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(register.this, "Failed to send verification email. Please try again.",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                            });
                                }

                            }


                        });
            }
        });
    }
}