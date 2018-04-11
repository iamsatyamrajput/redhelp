package com.example.satyam.redhelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.Context.MODE_PRIVATE;

public class SignupActivity extends AsyncTask<String, Void, String> {

    private Context context;
    String userName ;
    String passWord ;
    String age;
    String emailAddress;
    public SignupActivity(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... arg0) {
         userName = arg0[0];
         passWord = arg0[1];
         age = arg0[2];
         emailAddress = arg0[3];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?username=" + URLEncoder.encode(userName, "UTF-8");
            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");
            data += "&age=" + URLEncoder.encode(age, "UTF-8");
            data += "&emailaddress=" + URLEncoder.encode(emailAddress, "UTF-8");

            link = "http://redhelp.co.nf/signup.php" + data;
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
        UserSessionManager session=new UserSessionManager(context);
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show();
                    session.createUserLoginSession(userName);
                    context.startActivity(new Intent(context, LoginActivity.class));

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