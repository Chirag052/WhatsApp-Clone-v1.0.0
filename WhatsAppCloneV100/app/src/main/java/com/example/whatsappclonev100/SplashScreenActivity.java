package com.example.whatsappclonev100;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    PrefManager prefrenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        prefrenceManager = new PrefManager(this);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(FirebaseAuth.getInstance().getCurrentUser() !=null) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        if(prefrenceManager.isFirstTimeLaunch()) {


                            Intent intent = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), SignInPhoneActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                }
            }, 3000);

    }
}