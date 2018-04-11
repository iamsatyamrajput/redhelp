package com.example.satyam.redhelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class BloodTypeSelector extends AppCompatActivity {

    GridView blood_grid;
    Intent i;
    String[] name = {"AB+", "AB-", "A+", "A-", "B+", "B-", "O+", "O-"};
   // String name[];
    int[] imageId = {R.drawable.abplus, R.drawable.abminus, R.drawable.aplus, R.drawable.aminus, R.drawable.bplus, R.drawable.bminus, R.drawable.oplus, R.drawable.ominus};

  // int[] imageId = {R.drawable.search2,R.drawable.neednew2,R.drawable.person2,R.drawable.addrequest,R.drawable.aboutus2,R.drawable.becomedonor};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_type_selector);
        Blood_ImageAdapter adapter = new Blood_ImageAdapter(BloodTypeSelector.this, imageId);
        blood_grid = (GridView) findViewById(R.id.grid_view_blood_group);
        blood_grid.setAdapter(adapter);
       blood_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i = new Intent(BloodTypeSelector.this, MapsActivity.class);
                if (position == 0) {
                    i.putExtra("blood_group","AB%2B");
                    startActivity(i);
                } else if (position == 1) {
                    i.putExtra("blood_group","AB%2D");
                    startActivity(i);
                } else if (position == 2) {
                    i.putExtra("blood_group","A%2B");
                    startActivity(i);
                } else if (position == 3) {
                    i.putExtra("blood_group","A%2D");
                    startActivity(i);
                } else if (position == 4) {
                    i.putExtra("blood_group","B%2B");
                    startActivity(i);
                }
                else if (position == 5) {
                    i.putExtra("blood_group","B%2D");
                    startActivity(i);
                }
                else if (position == 6) {
                    i.putExtra("blood_group","O%2B");
                    startActivity(i);
                }
                else if (position == 7) {
                    i.putExtra("blood_group","O%2D");
                    startActivity(i);
                }
            }
        });

    }
}

