package com.example.whatsappclonev100;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsappclonev100.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ManageOTP extends AppCompatActivity {

    EditText et_manageOTP;
    Button bt_verifyAndSignIn;
    String phoneNo,mverificationId;
    FirebaseAuth mAuth;
    EditText inp1,inp2,inp3,inp4,inp5,inp6;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_otp);
        phoneNo=getIntent().getStringExtra("mobile").toString();
        firestore=FirebaseFirestore.getInstance();
        bt_verifyAndSignIn=findViewById(R.id.bt_verifyAndSignIn);
        mAuth=FirebaseAuth.getInstance();
        initOtp();
        TextView wrongnumber=findViewById(R.id.wrongnumber);
        inp1=findViewById(R.id.inp1);
        inp2=findViewById(R.id.inp2);
        inp3=findViewById(R.id.inp3);
        inp4=findViewById(R.id.inp4);
        inp5=findViewById(R.id.inp5);
        inp6=findViewById(R.id.inp6);
        setupOTPinputs();
        wrongnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(), SignInPhoneActivity.class);
                startActivity(intent);
            }
        });
        bt_verifyAndSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inp1.getText().toString().isEmpty() ||
                        inp2.getText().toString().isEmpty() ||
                        inp3.getText().toString().isEmpty() ||
                        inp4.getText().toString().isEmpty() ||
                        inp5.getText().toString().isEmpty() ||
                        inp6.getText().toString().isEmpty()
                        )
                {
                    Toast.makeText(ManageOTP.this, "Please enter valid code!", Toast.LENGTH_SHORT).show();

                    return;
                }else{
                    String code=inp1.getText().toString()+
                            inp2.getText().toString() +
                            inp3.getText().toString() +
                            inp4.getText().toString() +
                            inp5.getText().toString() +
                            inp6.getText().toString() ;

                            PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mverificationId,code);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });

    }

    private void setupOTPinputs(){
        inp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initOtp() {
        PhoneAuthProvider mCallbacks = null;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NotNull String s, @androidx.annotation.NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                mverificationId=s;
                            }

                            @Override
                            public void onVerificationCompleted(@NotNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NotNull FirebaseException e) {
                                Toast.makeText(ManageOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            DocumentReference documentReference = firestore.collection("Users").document(user.getUid());
                            Users users =new Users();
                            users.setUserMobileNo(user.getPhoneNumber());
                            users.setUserId(user.getUid());

                            documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.i("TAGG","Successfull signup");

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@androidx.annotation.NonNull @NotNull Exception e) {
                                    Log.i("TAGG","Sign in Error! Please try again");
                                }
                            });
                            Intent intent=new Intent(getApplicationContext(),SetProfileActivity.class);
                            startActivity(intent);


                        } else {
                            Toast.makeText(ManageOTP.this, "Sign in Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}