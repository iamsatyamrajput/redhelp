package com.example.satyam.redhelp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailRequest extends AppCompatActivity  implements OnMapReadyCallback {
TextView name,email,phone,deadline,b_group;
    double lat,log;
    Button btEmail,btCall;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detail_map);
        mapFragment.getMapAsync(DetailRequest.this);


       name=(TextView)findViewById(R.id.name);
        deadline=(TextView)findViewById(R.id.deadline);
        b_group=(TextView)findViewById(R.id.blood_group);
        btCall=(Button)findViewById(R.id.phone);
        btEmail=(Button)findViewById(R.id.mail);
        name.setText(getIntent().getStringExtra("name"));


       deadline.setText(getIntent().getStringExtra("deadline"));
        b_group.setText(getIntent().getStringExtra("b_group"));
       lat=Double.parseDouble(getIntent().getStringExtra("lat"));
        log=Double.parseDouble(getIntent().getStringExtra("log"));
    btCall.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ getIntent().getStringExtra("phone")));
            if (ContextCompat.checkSelfPermission(DetailRequest.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(DetailRequest.this,new String[]{Manifest.permission.CALL_PHONE},1);
            }else{
            startActivity(intent);}
        }
    });
        btEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
               // Toast.makeText(DetailRequest.this,getIntent().getStringExtra("mail") , Toast.LENGTH_SHORT).show();
                emailIntent.setType("plain/text");
                String email=getIntent().getStringExtra("mail");
                String[] TO = {email};
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,TO );
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "DONOR FOUND");


/* Send it off to the Activity-Chooser */
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DetailRequest.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch(requestCode){
        case 1: {
            if (grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }else{

            }
        }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "lat"+lat, Toast.LENGTH_SHORT).show();
        LatLng user = new LatLng(lat, log);
        mMap.addMarker(new
                MarkerOptions().position(user).title("request is from here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(user));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(user, 12));
        // Add a marker in Sydney, Australia, and move the camera.

    }
}
