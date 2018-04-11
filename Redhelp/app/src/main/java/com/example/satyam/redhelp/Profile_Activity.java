package com.example.satyam.redhelp;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Profile_Activity extends AppCompatActivity {
String userName;
    String myJSON;
    TextView b_group;
    TextView d_liter;
    TextView profile_email;
    TextView profile_phone,profile_username,free_ticket;
    UserSessionManager session;
    UserSessionManager profile;
    String username;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
       profile =new UserSessionManager(getApplicationContext());
       userName=profile.getUserDetails();
        d_liter=(TextView)findViewById(R.id.donated_blood);
         profile_email=(TextView)findViewById(R.id.profile_email);
        profile_phone=(TextView)findViewById(R.id.profile_phone);
        b_group=(TextView)findViewById(R.id.profile_blood_group);
        profile_username=(TextView)findViewById(R.id.profile_username);
        free_ticket=(TextView)findViewById(R.id.free_ticket) ;

        session=new UserSessionManager(Profile_Activity.this);
        username=session.getUserDetails();

        getData();
       /* final FloatingActionButton logout=(FloatingActionButton)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                profile.logoutUser();
                Toast.makeText(Profile_Activity.this, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        profile.logoutUser();

        switch (item.getItemId()) {
            case R.id.action_logout:
                profile.logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public void getData() {
        class GetJSonData extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String link;
                String data ;
                BufferedReader bufferedReader;
                String result;
                username=session.getUserDetails();
                try {
                   // data = "?username=" + URLEncoder.encode(userName, "UTF-8");
                    link = "http://redhelp.co.nf/profile.php?username="+ username;
                    URL url = new URL(link);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    //StringBuilder sb = new StringBuilder();
                    //result = bufferedReader.toString();

                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line +'\n');
                    }
                    result = sb.toString();



                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
                return result;
            }

            @Override
            protected void onPostExecute(String presult) {
               // Toast.makeText(Profile_Activity.this, "here 1", Toast.LENGTH_SHORT).show();
                myJSON = presult;
//                Toast.makeText(Profile_Activity.this, presult, Toast.LENGTH_LONG).show();

                    try {
                        JSONObject jsonProfile = new JSONObject(presult);
                        JSONArray profileArray = jsonProfile.getJSONArray("result");
                        JSONObject userProfile = profileArray.getJSONObject(0);
                        b_group.setText(userProfile.getString("b_group"));
                        d_liter.setText(userProfile.getString("d_litre"));
                        free_ticket.setText(userProfile.getString("d_litre"));
                        profile_email.setText(userProfile.getString("email"));
                        profile_phone.setText(userProfile.getString("phone"));
                        profile_username.setText(userName);

                       // Toast.makeText(Profile_Activity.this, "Toast " + b_group, Toast.LENGTH_SHORT).show();
                        //Toast.makeText(Profile_Activity.this, username, Toast.LENGTH_LONG).show();
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }



            }
        }
        GetJSonData g = new GetJSonData();
        g.execute();
    }

}
