package com.example.satyam.redhelp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Become_DonorActivity extends AppCompatActivity implements OnMapReadyCallback {
    GPSTracker gps;
    Button submit;
    RadioGroup radiogroup;
    EditText etAddress,etphone;
    TextView lat;
    TextView log;
    String  blood_group,address=null;
    double latitude,longitude;
    RadioButton radiobloodButton;
    Button getloc;
    int id=0;
    String result_lat;
    String result_log;
    UserSessionManager session;
    String username,phone;
    boolean rd=false;
    private GoogleMap mMap;
    String data;

    LatLng currentLocation;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become__donor);
        gps=new GPSTracker(this);
        submit=(Button)findViewById(R.id.submit);
         radiogroup=(RadioGroup)findViewById(R.id.blood_radio_group);
       etAddress=(EditText)findViewById(R.id.address);
 etphone=(EditText)findViewById(R.id.donor_phone);
       
       getloc=(Button)findViewById(R.id.btngetLocation);
        session=new UserSessionManager(Become_DonorActivity.this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        currentLocation=new LatLng(0,0);
        latitude=0.0;
        longitude=0.0;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(Become_DonorActivity.this);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    become_donor();
                }
            });

        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcoordinate();
            }
        });
            }

        void getcoordinate(){
            checkLocationPermission();
            latitude=gps.getLatitude();
            result_lat=Double.toString(latitude);
            longitude=gps.getLongitude();
           result_log=Double.toString(longitude);
            currentLocation=new LatLng(gps.getLatitude(),gps.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("you are here!!"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));


}  @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.

    }

        public void become_donor(){


              try {
                  id = radiogroup.getCheckedRadioButtonId();
                  radiobloodButton = (RadioButton) findViewById(id);
                  blood_group = (String) radiobloodButton.getText();
                  rd=true;
              }catch (Exception e){
                  rd=false;
                  e.printStackTrace();}
            phone=etphone.getText().toString();
               address=etAddress.getText().toString();

                if((latitude==0.0)||(longitude==0.0)||(address.equals(""))||(rd==false)||(phone.equals(null))){
                        if(latitude==0.0)
                        {
                            Toast.makeText(this, "please get your coordinate!", Toast.LENGTH_SHORT).show();
                        }
                        if(address.equals(""))
                        {
                            Toast.makeText(Become_DonorActivity.this, "please enter your address!", Toast.LENGTH_SHORT).show();
                        }
                        if(rd==false){
                            Toast.makeText(this, "please select your blood group!", Toast.LENGTH_SHORT).show();


                        }
                        if(phone.isEmpty()){
                        Toast.makeText(this, "please enter your correct phone No.", Toast.LENGTH_SHORT).show();

                        }


                }else{
                    if(address.length()<=8){
                        Toast.makeText(this, "please enter your correct address", Toast.LENGTH_SHORT).show();
                    } else{

                            if((phone.length()<10)||(phone.length()>11)) {
                                Toast.makeText(this, "please enter correct phone no.", Toast.LENGTH_SHORT).show();
                                 } else{

                                send();
                                  }


                    }
                    
                }

        }



    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

public void send()
{
    class Senddata extends AsyncTask<String, Void, String> {



        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {

            username=session.getUserDetails();
            
            String link;
            String data;
            BufferedReader bufferedReader;
            String result;

            try {

                data = "?b_group=" + URLEncoder.encode(blood_group, "UTF-8");
                data +="&username="+ URLEncoder.encode(username, "UTF-8");
                data += "&address=" + URLEncoder.encode(address, "UTF-8");
                data += "&lat=" + URLEncoder.encode(result_lat, "UTF-8");
                data += "&log=" + URLEncoder.encode(result_log, "UTF-8");
                data += "&phone=" + URLEncoder.encode(phone, "UTF-8");

                link = "http://redhelp.co.nf/become_donor.php" + data;
                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
           // Toast.makeText(Become_DonorActivity.this, username+"&", Toast.LENGTH_SHORT).show();
            String jsonStr = result;
            Context context=Become_DonorActivity.this;
           /// Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");
                    if (query_result.equals("SUCCESS")) {
                        Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
                        session.createDonor("true");
                        context.startActivity(new Intent(context, HomeActivity.class));

                    } else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                    }
                    else if (query_result.equals("INVALID")) {
                        Toast.makeText(context, "username exists,Please Login.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
Senddata s=new Senddata();

    Toast.makeText(this,phone, Toast.LENGTH_SHORT).show();
    s.execute();
}
}

