package com.example.satyam.redhelp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
;

import static com.example.satyam.redhelp.R.id.parent;
import static com.example.satyam.redhelp.R.styleable.View;

public class InNeedActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> arraylist=new ArrayList<HashMap<String,String>>();
    ListViewAdapter adapter;
    ListView listview;
    ProgressDialog asyncDialog;


    static final String USERNAME = "username";
    static final String B_GROUP = "b_group";
    static final String PHONE = "aphone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_need);
       // Toast.makeText(this, "inflate is complete", Toast.LENGTH_SHORT).show();
        asyncDialog = new ProgressDialog(InNeedActivity.this);

        listview = (ListView) findViewById(R.id.listview);
        getData();
        adapter = new ListViewAdapter(InNeedActivity.this, arraylist);
        // Set the adapter to the ListView
        //Toast.makeText(this, "here adapter"+adapter.getItemViewType(1), Toast.LENGTH_LONG).show();

        //Toast.makeText(this, "here 1", Toast.LENGTH_LONG).show();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(InNeedActivity.this, "hello", Toast.LENGTH_SHORT).show();
                //Toast.makeText(InNeedActivity.this, arraylist.get(position).get("name"), Toast.LENGTH_SHORT).show();
                Intent i=new Intent(InNeedActivity.this,DetailRequest.class);
                i.putExtra("name",arraylist.get(position).get("name"));
                i.putExtra("deadline",arraylist.get(position).get("end_date"));
                i.putExtra("phone",arraylist.get(position).get("phone"));
                i.putExtra("mail",arraylist.get(position).get("email"));
                i.putExtra("lat",arraylist.get(position).get("lat"));
                i.putExtra("log",arraylist.get(position).get("log"));
               // Toast.makeText(InNeedActivity.this, "lat"+arraylist.get(position).get("log")+arraylist.get(position).get("phone"), Toast.LENGTH_SHORT).show();
                i.putExtra("b_group",arraylist.get(position).get("b_group"));
                startActivity(i);
            }
        });
    }


        public void getData() {
        class GetJSonData extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                asyncDialog.setMessage("Loading Urgent Need Blood List");
                asyncDialog.show();
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... params) {

                String link;
                String data;
                BufferedReader bufferedReader;
                String inneed;
                try {
                    // data = "?username=" + URLEncoder.encode(userName, "UTF-8");
                    link = "http://redhelp.co.nf/inneed.php";
                    URL url = new URL(link);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    //StringBuilder sb = new StringBuilder();
                    //result = bufferedReader.toString();

                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line + '\n');
                    }
                    inneed = sb.toString();


                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
                try {
                    JSONObject jsonInneed = new JSONObject(inneed);
                    JSONArray inneedArray = jsonInneed.getJSONArray("result");

                    JSONObject jsonobject;
                        int i=inneedArray.length()-1;
                    for (; i >=0; i--) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        jsonobject = inneedArray.getJSONObject(i);
                       // Toast.makeText(InNeedActivity.this, "in loop", Toast.LENGTH_SHORT).show();
                       // Log.e("sdsd","for");
                        map.put("name", jsonobject.getString("name"));
                        map.put("b_group", jsonobject.getString("b_group"));
                        map.put("email", jsonobject.getString("email"));
                        map.put("phone", jsonobject.getString("phone"));
                        map.put("start_date", jsonobject.getString("start_date"));
                        map.put("end_date", jsonobject.getString("end_date"));
                        map.put("lat", jsonobject.getString("lat"));
                        map.put("log", jsonobject.getString("log"));
                       // Toast.makeText(InNeedActivity.this, "in loop", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(InNeedActivity.this,jsonobject.getString("username") , Toast.LENGTH_SHORT).show();

                        arraylist.add(map);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return inneed;
            }

            @Override
            protected void onPostExecute(String inneed) {


                //Toast.makeText(InNeedActivity.this, inneed, Toast.LENGTH_LONG).show();
                //inflating listview

                // Pass the results into ListViewAdapter.java.java

                listview.setAdapter(adapter);
                asyncDialog.dismiss();


            }


        }


        GetJSonData g = new GetJSonData();
        g.execute();
    }


}

