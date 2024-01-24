package com.example.onlineparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class adminpage extends AppCompatActivity {
    Button data, qrgenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        data = findViewById(R.id.data);
        qrgenerator = findViewById(R.id.qrgenerator);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminpage.this, parkingdata.class);
                startActivity(intent);
                finish();

            }
        });
        qrgenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminpage.this, qrscanner.class);
                startActivity(intent);
                finish();

            }
        });
    }
}