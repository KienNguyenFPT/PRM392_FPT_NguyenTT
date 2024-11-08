package com.prm392.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class PhoneOtpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtOTP;
    private Button verifyOTPBtn;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_otp);

        mAuth = FirebaseAuth.getInstance();
        edtOTP = findViewById(R.id.editTextOtp);
        verifyOTPBtn = findViewById(R.id.buttonVerifyOtp);

        // Get the verificationId from the previous activity
        verificationId = getIntent().getStringExtra("verificationId");

        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtOTP.getText().toString())) {
                    Toast.makeText(PhoneOtpActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    String otp = edtOTP.getText().toString();
                    verifyCode(otp);
                }
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Handle successful sign-in (e.g., open another activity)
                            Intent intent = new Intent(PhoneOtpActivity.this, HomePageActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(PhoneOtpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}