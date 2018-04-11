package com.example.satyam.redhelp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final ImageView splash=(ImageView)findViewById(R.id.splash);
        final SharedPreferences prefs = getSharedPreferences("User", MODE_PRIVATE);



        splash.post(new Runnable() {

            @Override
            public void run() {

                ((AnimationDrawable) splash.getBackground()).start();
            }



        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                 /*   startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();*/



                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
                    // ask him to enter his credentials

            }
        }, 3000);
    }
}
