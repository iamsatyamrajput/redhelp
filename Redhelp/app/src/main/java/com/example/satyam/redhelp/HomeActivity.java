package com.example.satyam.redhelp;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import tyrantgit.explosionfield.ExplosionField;

public class HomeActivity extends AppCompatActivity {
    Bundle savedInstanceState;
    GridView grid;
    Intent i;
    String isdonor;
    String[] name = {"Donor search","Urgent need","Profile","Add Request","About Us","Become Donor"} ;
    UserSessionManager session;
    int[] imageId = {R.drawable.search2,R.drawable.neednew2,R.drawable.person2,R.drawable.addrequest,R.drawable.aboutus2,R.drawable.becomedonor};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        checkLocationPermission();
        session=new UserSessionManager(getApplicationContext());
              ImageAdapter adapter = new ImageAdapter(HomeActivity.this, name, imageId);
        grid=(GridView)findViewById(R.id.grid_view);
        grid.setAdapter(adapter);
isdonor=session.isDonor();
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                 i=new Intent(HomeActivity.this,BloodTypeSelector.class);
                    startActivity(i);
                } else if(position==1)
                {
                    i=new Intent(HomeActivity.this,InNeedActivity.class);
                    startActivity(i);
                } else if(position==4)
                {
                    i=new Intent(HomeActivity.this,AboutUs.class);
                    startActivity(i);
                }else if(position==2)
                { if(isdonor=="false") {
                    i = new Intent(HomeActivity.this, Become_DonorActivity.class);
                    Toast.makeText(HomeActivity.this, "You need to become a donor first!!", Toast.LENGTH_SHORT).show();
                startActivity(i);
                }
                else
                   // Toast.makeText(HomeActivity.this, isdonor, Toast.LENGTH_SHORT).show();
                    i=new Intent(HomeActivity.this,Profile_Activity.class);
                    startActivity(i);
                }else if(position==5){
                    if(isdonor.contains("true")){
                        Toast.makeText(HomeActivity.this, "You are already a donor!!!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(HomeActivity.this, session.isDonor(), Toast.LENGTH_SHORT).show();
                    i=new Intent(HomeActivity.this,Become_DonorActivity.class);
                    startActivity(i);}
                }
                else if(position==3)
                {

                   // Toast.makeText(HomeActivity.this, , Toast.LENGTH_SHORT).show();
                i=new Intent(HomeActivity.this,Add_Request_Activity.class);
                    startActivity(i);
                }
            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


}