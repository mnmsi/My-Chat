package com.example.msi_.mychat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SignIn extends AppCompatActivity {

    Button signout, upload_bttn, showData, chat;
    private FirebaseAuth mAuth;
    TextView username;

    static String LoggedIn_User_Phone;
    public static int Device_Width;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        signout = (Button)findViewById(R.id.signout);
        username = (TextView) findViewById(R.id.tvName);
        upload_bttn = (Button)findViewById(R.id.upload);
        showData = (Button)findViewById(R.id.show_data);
        chat = (Button)findViewById(R.id.chat_button);


       if (BeginActivity.mDatabase == null) {

            BeginActivity .mDatabase = FirebaseDatabase.getInstance();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null) {

            this.finish();
            startActivity(new Intent(getApplicationContext(),BeginActivity.class));
        }


        FirebaseUser user = mAuth.getCurrentUser();
        Log.d("LOGGED", "FirebaseUser: " + user);

        if (user != null) {

            username.setText("Welcome, " + user.getDisplayName());
            LoggedIn_User_Phone =user.getPhoneNumber();
        }

        DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
        Device_Width = metrics.widthPixels;

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), BeginActivity.class));
            }
        });

        upload_bttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),UploadInfo.class));
            }
        });

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),ShowData.class));
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChatActivity.class));
            }
        });

    }

}
