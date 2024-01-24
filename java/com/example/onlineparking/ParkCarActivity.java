package com.example.onlineparking;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineparking.databinding.ActivityParkCarBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class ParkCarActivity extends AppCompatActivity {
    ActivityParkCarBinding binding;
    FirebaseFirestore fSTore;
    FirebaseAuth fAuth;
    ProgressDialog dialog;
    SharedPreferences prefs;
    RadioGroup amountRadioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParkCarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fAuth=FirebaseAuth.getInstance();
        fSTore=FirebaseFirestore.getInstance();
        dialog=new ProgressDialog(this);

        amountRadioGroup = findViewById(R.id.amount_radio_group);
        // Initialize SharedPreferences object
        prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Retrieve values from SharedPreferences
        String driverName = prefs.getString("driverName", "");
        String driverNumber = prefs.getString("driverNumber", "");
        String numberPlate = prefs.getString("numberPlate", "");
        String vehicleType = prefs.getString("vehicleType", "");

        binding.driverName.setText(driverName);
        binding.driverNumber.setText(driverNumber);
        binding.numberPlate.setText(numberPlate);
        binding.vehicleType.setText(vehicleType);

        binding.parkCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String driverName = binding.driverName.getText().toString();
                String driverNumber = binding.driverNumber.getText().toString();
                String numberPlate = binding.numberPlate.getText().toString();
                String vehicleType = binding.vehicleType.getText().toString();
                String amount = binding.amount.getText().toString();

                // Get the selected radio button id
                int selectedId = amountRadioGroup.getCheckedRadioButtonId();

                // Set the amount value based on the selected radio button
                if (selectedId == R.id.radio1) {
                    amount = "5000";
                } else if (selectedId == R.id.radio2) {
                    amount = "10000";
                } else if(selectedId == R.id.radio3){
                    amount = "15000";
                }


                long time = Calendar.getInstance().getTimeInMillis();
                String id = UUID.randomUUID().toString();

                ParkCarModel parkCarModel = new ParkCarModel();

                parkCarModel.setId(id);
                parkCarModel.setNumberPlate(numberPlate);
                parkCarModel.setDriverName(driverName);
                parkCarModel.setTime(time);
                parkCarModel.setVehicleType(vehicleType);

                parkCarModel.setFee(amount);
                parkCarModel.setDriverNumber(driverNumber);
                parkCarModel.setUserId(fAuth.getCurrentUser().getUid());
                parkCarModel.setStatus("Parked");

                // Save values to SharedPreferences
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("driverName", driverName);
                editor.putString("driverNumber", driverNumber);
                editor.putString("numberPlate", numberPlate);
                editor.putString("vehicleType", vehicleType);
                editor.apply();

                dialog.setTitle("Uploading");
                dialog.setMessage("Loading");
                dialog.show();
                fSTore.collection("parking")
                        .document(id)
                        .set(parkCarModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                dialog.cancel();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.cancel();
                                Toast.makeText(ParkCarActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });


    }
}