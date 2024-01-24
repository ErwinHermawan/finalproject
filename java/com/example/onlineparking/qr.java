package com.example.onlineparking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class qr extends AppCompatActivity {
Button scan;


    FirebaseUser user;
    TextView email;
    Context mContext;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        email = findViewById(R.id.email);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();



        scan = findViewById(R.id.scan);
        scan.setOnClickListener(view -> 
        {
            scanCode();
        });
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);

    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result ->
    {
        if(result.getContents() !=null){

            AlertDialog.Builder builder = new AlertDialog.Builder(qr.this);
            String hasil = getMdHash(user.getEmail().toString());
            builder.setTitle(hasil);
            builder.setMessage(result.getContents());

            builder.setPositiveButton("Tap OK After Complete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                    Intent intent = new Intent(qr.this, successful.class);
                    startActivity(intent);
                    finish();
                }
            }).show();
        }
    }
    );

    private String getMdHash(String toString) {
        String MD5 = "MD5";
        try{
            //this create md5 hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(toString.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuilder hexString = new StringBuilder();

            //to create hex string
            for(byte aMsgDigest : messageDigest){
                String h = Integer.toHexString(0xFF & aMsgDigest);

                while (h.length()<2)
                    h = "0" +h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}