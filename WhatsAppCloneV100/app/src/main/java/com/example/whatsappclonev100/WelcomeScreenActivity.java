package com.example.whatsappclonev100;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeScreenActivity extends AppCompatActivity {
    private PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Button bt=findViewById(R.id.agreeandcontinue);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignInPhoneActivity.class);
                startActivity(intent);
                launchHomeScreen();

            }
        });

        }
    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent=new Intent(getApplicationContext(), SignInPhoneActivity.class);
            startActivity(intent);

        }
        finish();
    }
}