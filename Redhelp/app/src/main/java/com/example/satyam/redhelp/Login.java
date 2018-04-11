package com.example.satyam.redhelp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by satyam on 10-03-2017.
 */

public class Login extends AsyncTask<String,Void,String> {

    private Context context;

    String userName;

    public Login(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }
    @Override
    protected String doInBackground(String... arg0) {
        userName = arg0[0];
        String passWord = arg0[1];

        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?username=" + URLEncoder.encode(userName, "UTF-8");
            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");

            link = "http://redhelp.co.nf/login.php" + data;
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
                //Toast.makeText(context, jsonStr , Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");
                    if (query_result.equals("USER")) {
                        Toast.makeText(context, "Welcome.", Toast.LENGTH_SHORT).show();
                        session.createUserLoginSession(userName);
                        context.startActivity(new Intent(context, HomeActivity.class));


                    }
                    else if(query_result.equals("DONOR"))
                    {
                        session.createUserLoginSession(userName);
                        session.createDonor("true");
                        context.startActivity(new Intent(context, HomeActivity.class));
                    }

                    else if (query_result.equals("FAILURE")) {
                        Toast.makeText(context, "invalid username or password", Toast.LENGTH_SHORT).show();
                    } else if(query_result.equals("ERROR")){
                        Toast.makeText(context, "Error 404!!", Toast.LENGTH_SHORT).show();
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


