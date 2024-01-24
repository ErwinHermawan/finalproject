package com.example.onlineparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
Button Register;
Button Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Register = findViewById(R.id.register);
        Login = findViewById(R.id.login);


    }
        public void Register (View view){
        startActivity(new Intent(MainActivity.this, register.class));
    }
    public void Login (View view){
        startActivity(new Intent(MainActivity.this, loginpage.class));
    }
    public void AdminLogin (View view){
        startActivity(new Intent(MainActivity.this, admin.class));
    }
}