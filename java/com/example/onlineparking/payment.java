package com.example.onlineparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class payment extends AppCompatActivity {
    private TextView timestampTextView;
    ImageView scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        scanner = findViewById(R.id.scanner);
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(payment.this, qr.class);
                startActivity(intent);
                finish();
            }
        });
        /*long timestamp = System.currentTimeMillis();

        // Convert the timestamp to a Date object
        Date date = new Date(timestamp);

        // Find the TextView in the layout and set its text to the timestamp
        timestampTextView = findViewById(R.id.timestamptextview);
        timestampTextView.setText(date.toString());
*/
    }

        }

