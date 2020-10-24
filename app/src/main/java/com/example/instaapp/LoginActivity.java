 package com.example.instaapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

 public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(ParseUser.getCurrentUser() !=null){
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin= findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG,"onClick SignUp button");
                ParseUser user = new ParseUser();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                user.setUsername(username);
                user.setPassword(password);

                user.signUpInBackground(new SignUpCallback() {
                    public void done(ParseException e) {
                        if (e == null) {
                            // Hooray! Let them use the app now.
                            loginUser(username,password);
                        } else {
                            // Sign up didn't succeed. Look at the ParseException
                            // to figure out what went wrong
                            Log.i(TAG, "Problem encountered when Signing Up", e);
                        }
                    }
                });

            }
        });

     btnLogin.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View view) {
             Log.i(TAG, "onClick Login button");
             String username = etUsername.getText().toString();
             String password = etPassword.getText().toString();
             loginUser(username, password);
         }
     });

 }

    private void loginUser(String username, String password) {
        Log.i(TAG,"Attempting a login user "+username);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e!=null){
                    Log.e(TAG,"Issue with login",e);
                    Toast.makeText(LoginActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                    return;
                }
                //TODO : navigate to the main activity if the user has signed in properly
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }

     private void goMainActivity() {
         Intent i = new Intent(this,MainActivity.class);
         startActivity(i);
         finish();
     }

 }