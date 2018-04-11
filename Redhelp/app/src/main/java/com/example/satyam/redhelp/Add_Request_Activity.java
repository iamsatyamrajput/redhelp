package com.example.satyam.redhelp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Add_Request_Activity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {
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
    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    String startDate,endDate="",eMail;
    EditText etmail;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__request_);
        checkLocationPermission();
        gps=new GPSTracker(this);
        id();
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);
        setDateTimeField();

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        getloc=(Button)findViewById(R.id.btngetLocation);
        session=new UserSessionManager(Add_Request_Activity.this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        currentLocation=new LatLng(0,0);
        latitude=0.0;
        longitude=0.0;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(Add_Request_Activity.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_request();
            }
        });

        getloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcoordinate();
            }
        });
    }

    public void id(){
        etmail=(EditText)findViewById(R.id.etmail);
        submit=(Button)findViewById(R.id.submit);
        radiogroup=(RadioGroup)findViewById(R.id.blood_radio_group);
        etAddress=(EditText)findViewById(R.id.address);
        etphone=(EditText)findViewById(R.id.donor_phone);
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
    }
    void getcoordinate(){

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

    public void add_request(){


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
        eMail=etmail.getText().toString();

        if((latitude==0.0)||(longitude==0.0)||(address.equals(""))||(rd==false)||(phone.equals(null))||(endDate.isEmpty())||(eMail.isEmpty())){
            if(latitude==0.0)
            {
                Toast.makeText(this, "please get your coordinate!", Toast.LENGTH_SHORT).show();
            }
            if(address.equals(""))
            {
                Toast.makeText(Add_Request_Activity.this, "please enter your Name!", Toast.LENGTH_SHORT).show();
            }
            if(rd==false){
                Toast.makeText(this, "please select your blood group!", Toast.LENGTH_SHORT).show();

            }
            if(phone.isEmpty()){
                Toast.makeText(this, "please enter your correct phone No.", Toast.LENGTH_SHORT).show();

            }
            if(endDate.isEmpty()){
                Toast.makeText(this, "please select your Deadline", Toast.LENGTH_SHORT).show();
            }
            if(eMail.isEmpty()){
                Toast.makeText(this, "please enter your email", Toast.LENGTH_SHORT).show();
            }


        }else{
            if(address.length()<=3){
                Toast.makeText(this, "name length is small", Toast.LENGTH_SHORT).show();
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

                BufferedReader bufferedReader;
                String result;

                try {

                    data = "?b_group=" + URLEncoder.encode(blood_group, "UTF-8");
                    data += "&name=" + URLEncoder.encode(address, "UTF-8");
                    data += "&lat=" + URLEncoder.encode(result_lat, "UTF-8");
                    data += "&log=" + URLEncoder.encode(result_log, "UTF-8");
                    data += "&phone=" + URLEncoder.encode(phone, "UTF-8");
                    data += "&start_date=" + URLEncoder.encode(startDate, "UTF-8");
                    data += "&end_date=" + URLEncoder.encode(endDate, "UTF-8");
                    data += "&email=" + URLEncoder.encode(eMail, "UTF-8");

                    link = "http://redhelp.co.nf/add_request.php" + data;
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
               // Toast.makeText(Add_Request_Activity.this,data, Toast.LENGTH_LONG).show();
                String jsonStr = result;
                Context context=Add_Request_Activity.this;
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

    public void setDateTimeField() {


        Calendar newCalendar = Calendar.getInstance();
        fromDateEtxt.setText(SimpleDateFormat.getDateInstance().format(new Date()));
        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                endDate=dateFormatter.format(newDate.getTime());

                toDateEtxt.setText(endDate);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        startDate=SimpleDateFormat.getDateInstance().format(new Date());
    }



    public void onClick(View view) {

        if(view == toDateEtxt) {

            toDatePickerDialog.show();
        }
    }

}

