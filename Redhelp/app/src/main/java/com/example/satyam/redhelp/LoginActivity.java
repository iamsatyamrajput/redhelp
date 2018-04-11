package com.example.satyam.redhelp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etUserName, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UserSessionManager session = new UserSessionManager(getApplicationContext());

        if (session.checkLogin()) {
            Toast.makeText(this, "logged", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "not logged", Toast.LENGTH_SHORT).show();

            etUserName = (EditText) findViewById(R.id.etUserName);
            etPassword = (EditText) findViewById(R.id.etPassword);
            Button bLogin = (Button) findViewById(R.id.bLogin);
            Button bRegister = (Button) findViewById(R.id.bRegister);
            //Button bForgot=(Button)findViewById(R.id.bForgot);


            bRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(register);

                }
            });

        }
    }

    public void login(View v) {

        String userName = etUserName.getText().toString();
        String passWord = etPassword.getText().toString();
       // Toast.makeText(this, "logging in.....", Toast.LENGTH_SHORT).show();
        new Login(this).execute(userName, passWord);
    }


    boolean doubleBackToExitPressedOnce = false;

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            return;
        } this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
