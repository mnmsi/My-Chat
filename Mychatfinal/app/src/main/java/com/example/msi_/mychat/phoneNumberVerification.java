package com.example.msi_.mychat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class phoneNumberVerification extends AppCompatActivity {

    EditText MobileNumber, OTPEditview, name;
    Button Submit, OTPButton, Done;
    TextView Textview, Otp, textView4, textView5, textView6, textView7, textView8;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    boolean mVerificationInProgress = false;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseUser user;
    public static FirebaseDatabase mDatabase;
    static String LoggedIn_User_Phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);
        Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();

        MobileNumber = (EditText) findViewById(R.id.mobileNumber);
        Submit = (Button) findViewById(R.id.submit);
        OTPEditview = (EditText) findViewById(R.id.otp_editText);
        OTPButton = (Button) findViewById(R.id.otp_button);
        Textview = (TextView) findViewById(R.id.textView);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        textView7 = (TextView) findViewById(R.id.textView7);
        textView8 = (TextView) findViewById(R.id.textView8);
        name = (EditText)findViewById(R.id.etName);
        Otp = (TextView) findViewById(R.id.otp);
        Done = (Button) findViewById(R.id.done);
        name = (EditText)findViewById(R.id.etName);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();
        Log.d("LOGGED", "user: " + user);

        if (user != null) {
            LoggedIn_User_Phone =user.getPhoneNumber();
        }


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(phoneNumberVerification.this,"verification Success"+ phoneAuthCredential,Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                progressBar.setVisibility(View.GONE);

                if (e instanceof FirebaseAuthInvalidCredentialsException){

                    Toast.makeText(phoneNumberVerification.this,"Please Enter Your Phone Number",Toast.LENGTH_LONG).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Toast.makeText(phoneNumberVerification.this,"Time out" ,Toast.LENGTH_LONG).show();

                }
                else{

                    Toast.makeText(phoneNumberVerification.this,"No Internet Connection",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                Toast.makeText(phoneNumberVerification.this,"Verification code sent to mobile",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                mVerificationId = verificationId;
                mResendToken = token;
                textView4.setVisibility(View.GONE);
                textView5.setVisibility(View.GONE);
                textView6.setVisibility(View.GONE);
                textView7.setVisibility(View.GONE);
                textView8.setVisibility(View.GONE);
                MobileNumber.setVisibility(View.GONE);
                Submit.setVisibility(View.GONE);
                Textview.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                Done.setVisibility(View.GONE);
                OTPButton.setVisibility(View.VISIBLE);
                OTPEditview.setVisibility(View.VISIBLE);
                Otp.setVisibility(View.VISIBLE);

            }
        };



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+880"+MobileNumber.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        phoneNumberVerification.this,
                        mCallbacks);
                progressBar.setVisibility(View.VISIBLE);
            }
        });


        OTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otpEditview = OTPEditview.getText().toString();

                if (TextUtils.isEmpty(otpEditview)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Code!", Toast.LENGTH_SHORT).show();
                    return;
                }

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otpEditview);
                signInWithPhoneAuthCredential(credential);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            textView4.setVisibility(View.GONE);
                            textView5.setVisibility(View.GONE);
                            textView6.setVisibility(View.GONE);
                            textView7.setVisibility(View.GONE);
                            textView8.setVisibility(View.GONE);
                            MobileNumber.setVisibility(View.GONE);
                            Submit.setVisibility(View.GONE);
                            Textview.setVisibility(View.GONE);
                            name.setVisibility(View.VISIBLE);
                            Done.setVisibility(View.VISIBLE);
                            OTPButton.setVisibility(View.GONE);
                            OTPEditview.setVisibility(View.GONE);
                            Otp.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(phoneNumberVerification.this,"Verification done",Toast.LENGTH_LONG).show();
                            //FirebaseUser user = task.getResult().getUser();

                            Done.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String Name = name.getText().toString();

                                    if (TextUtils.isEmpty(Name)) {
                                        Toast.makeText(getApplicationContext(), "Enter Your name!", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    userProfile();

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String UserID = user.getPhoneNumber().toString();
                                    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference mRootRef = mDatabase.getInstance().getReference();

                                    DatabaseReference ref1= mRootRef.child("Users_phone").child(UserID);

                                    ref1.child("Name").setValue(Name);
                                    ref1.child("Image_Url").setValue("Null");
                                    ref1.child("Phone").setValue(user.getPhoneNumber());

                                    //Log.d("TESTING", "Sign up Successful" + task.isSuccessful());
                                    Toast.makeText(phoneNumberVerification.this, "Account Created ", Toast.LENGTH_SHORT).show();
                                    Log.d("TESTING", "Created Account");

                                    Toast.makeText(phoneNumberVerification.this,"Verification done",Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(phoneNumberVerification.this, SignIn.class);
                                    finish();
                                    startActivity(i);

                                }
                            });

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(phoneNumberVerification.this,"Verification failed code invalid",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

    }
    private void userProfile() {

        FirebaseUser user = mAuth.getCurrentUser();

        if(user!= null) {

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.getText().toString().trim())
                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))  // here you can set image link also.
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TESTING", "User profile updated.");
                            }
                        }
                    });
        }
    }
}