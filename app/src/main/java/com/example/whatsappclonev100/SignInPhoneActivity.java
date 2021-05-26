package com.example.whatsappclonev100;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class SignInPhoneActivity extends AppCompatActivity {

    CountryCodePicker ccp;
    EditText et_phoneno;
    Button bt_getOtp;
    FirebaseAuth auth;
    EditText et_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_phone);
        et_phoneno = findViewById(R.id.et_phoneno);
        bt_getOtp = findViewById(R.id.getOtp);
        ccp=findViewById(R.id.ccp);
        et_phone=findViewById(R.id.et_phoneno);

        ccp.registerCarrierNumberEditText(et_phoneno);
        auth = FirebaseAuth.getInstance();

        bt_getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_phone.getText().toString().trim().equalsIgnoreCase("")) {
                    et_phone.setError("This field can not be blank");
                }else {
                    Intent intent = new Intent(getApplicationContext(), ManageOTP.class);
                    intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace(" ", ""));
                    startActivity(intent);
                }




            }
        });

    }
}