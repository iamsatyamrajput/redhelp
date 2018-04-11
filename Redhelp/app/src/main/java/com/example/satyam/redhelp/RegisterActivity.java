package com.example.satyam.redhelp;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {

    private EditText etUserName, etPassword, etAge, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAge = (EditText) findViewById(R.id.etAge);
        etEmail = (EditText) findViewById(R.id.etEmail);
    }

    public void signup(View v) {
        String userName = etUserName.getText().toString();
        String passWord = etPassword.getText().toString();
        String age = etAge.getText().toString();
        String emailAddress = etEmail.getText().toString();
        if(userName.equals("")||emailAddress.equals("")||passWord.equals("")||age.equals(""))
        {
            if(userName.equals(""))
            {Toast.makeText(getApplicationContext(),"Enter your Name",Toast.LENGTH_SHORT).show();}
            if(emailAddress.equals(""))
            {Toast.makeText(getApplicationContext(),"Enter your Email",Toast.LENGTH_SHORT).show();}
            if(passWord.equals(""))
            {Toast.makeText(getApplicationContext(),"Enter your Password",Toast.LENGTH_SHORT).show();}
            if(age.equals(""))
            {Toast.makeText(getApplicationContext(),"Enter you age",Toast.LENGTH_SHORT).show();}

        }else
        {
            if(!emailAddress.contains("@")||!emailAddress.contains("."))
            {Toast.makeText(getApplicationContext(),"email id is invalid",Toast.LENGTH_SHORT).show();}
            else{
                if(age.length()>2)
                    {Toast.makeText(getApplicationContext(),"age must be in 18+",Toast.LENGTH_SHORT).show();}
                    else{
                        if(passWord.length()<8)
                        {Toast.makeText(getApplicationContext(),"weak password",Toast.LENGTH_SHORT).show();}
                        else{

                        Toast.makeText(this, "Signing up...", Toast.LENGTH_SHORT).show();
                        new SignupActivity(this).execute(userName, passWord, age, emailAddress);
                           // Toast.makeText(this, "i am here", Toast.LENGTH_SHORT).show();

                }



        }
            }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   @Override*/
   /* public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}}}