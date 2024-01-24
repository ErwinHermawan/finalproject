package com.example.onlineparking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineparking.databinding.ActivitySuccessfulBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class successful extends AppCompatActivity implements TicketAdapterInterface {
    ActivitySuccessfulBinding binding;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TicketAdapter adapter;
    int parked =0,paid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful);
        binding = ActivitySuccessfulBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        adapter = new TicketAdapter(this,this);
        binding.carsRecycler.setAdapter(adapter);
        binding.carsRecycler.setLayoutManager(new LinearLayoutManager(this));

        loaddata();
    }
    @Override
    protected void onStart() {
        super.onStart();
        ProgressDialog progressDialo = new ProgressDialog(this);
        progressDialo.setTitle("Wait");
        progressDialo.setMessage("Processing");
        if(fAuth.getCurrentUser()==null){
            progressDialo.show();
            fAuth.signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            progressDialo.cancel();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialo.cancel();
                            Toast.makeText(successful.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        }else {
            progressDialo.cancel();
        }
    }
        private void loaddata(){
            fStore.collection("parking")
                    //the where equalto is for each account filter
                     .whereEqualTo("userId", fAuth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot ds:dsList){
                                ParkCarModel model=ds.toObject(ParkCarModel.class);
                                if(model.getStatus().equals("Parked")){
                                    String fee= model.getFee();
                                    parked=parked+Integer.parseInt(fee);

                                    long parkedTime = System.currentTimeMillis() - model.getTime();
                                    if (parkedTime > 60 * 60 * 1000 && parkedTime <= 3 * 60 * 60 * 1000) {
                                        // If parked time is more than 1 hour and less than or equal to 3 hours, change status to "Extend"
                                        model.setStatus("Extend");
                                        // Add 5999 to the fee
                                        int newFee = Integer.parseInt(fee) + 9999;
                                        model.setFee(Integer.toString(newFee));
                                    } else if (parkedTime > 3 * 60 * 60 * 1000 && parkedTime <= 5 * 60 * 60 * 1000) {
                                        // If parked time is more than 3 hours and less than or equal to 5 hours, change status to "Extend"
                                        model.setStatus("Extend");
                                        // Add 7999 to the fee
                                        int newFee = Integer.parseInt(fee) + 9999;
                                        model.setFee(Integer.toString(newFee));
                                    } else if (parkedTime > 5 * 60 * 60 * 1000) {
                                        // If parked time is more than 5 hours, change status to "Extend"
                                        model.setStatus("Extend");
                                        // Add 9999 to the fee
                                        int newFee = Integer.parseInt(fee) + 9999;
                                        model.setFee(Integer.toString(newFee));
                                    }
                                    parked=parked+Integer.parseInt(model.getFee());
                                }
                                if(model.getStatus().equals("Paid")){
                                    String fee= model.getFee();
                                    paid=paid+Integer.parseInt(fee);
                                }
                                adapter.add(model);

                            }
                            binding.paid.setText(paid+"");
                            binding.pending.setText(parked+"");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(successful.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


    }

    @Override
    public void OnLongClick(int pos, String id) {
        Intent intent = new Intent(successful.this, qr.class);
        startActivity(intent);
        finish();
    }
}