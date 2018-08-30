package com.example.msi_.mychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class BeginActivity extends AppCompatActivity {

    Button AgreeButton;
    TextView WelcomeText, textView2, textView3;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    static String LoggedIn_User_Phone;
    public static FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        if (mDatabase == null) {

            mDatabase = FirebaseDatabase.getInstance();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

        mAuth = FirebaseAuth.getInstance();

        AgreeButton = (Button) findViewById(R.id.agreeButton);
        WelcomeText = (TextView) findViewById(R.id.welcomeText);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);

        if(mAuth.getCurrentUser() != null) {

            finish();
            startActivity(new Intent(getApplicationContext(),SignIn.class));
        }

        user = mAuth.getCurrentUser();
        Log.d("LOGGED", "user: " + user);

        if (user != null) {
            LoggedIn_User_Phone =user.getPhoneNumber();
        }

        AgreeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BeginActivity.this, phoneNumberVerification.class);
                startActivity(intent);
            }

        });
    }
}
