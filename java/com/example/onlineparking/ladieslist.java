package com.example.onlineparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ladieslist extends AppCompatActivity {
ImageView imgbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ladieslist);
        imgbutton = findViewById(R.id.image);
        imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ladieslist.this, ParkCarActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}