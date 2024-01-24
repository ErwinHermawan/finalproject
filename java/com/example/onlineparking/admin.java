package com.example.onlineparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class admin extends AppCompatActivity {
    EditText editText;
    RadioButton radioButtonA;
    RadioButton radioButtonB;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editText = findViewById(R.id.editText);
        radioButtonA = findViewById(R.id.radioButtonA);
        radioButtonB = findViewById(R.id.radioButtonB);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = editText.getText().toString().trim();

                if (radioButtonA.isChecked() && inputText.equals("abcde")) {
                    Intent intent = new Intent(admin.this, adminpage.class);
                    intent.putExtra("message", "abcde");
                    startActivity(intent);
                } else if (radioButtonB.isChecked() && inputText.equals("abc")) {
                    Intent intent = new Intent(admin.this, adminpage.class);
                    intent.putExtra("message", "abc");
                    startActivity(intent);
                } else {
                    Toast.makeText(admin.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}