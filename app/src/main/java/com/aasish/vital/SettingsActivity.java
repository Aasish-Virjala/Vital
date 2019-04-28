package com.aasish.vital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsActivity extends AppCompatActivity {
    private TextView mEmail, myourEmail;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button mChangePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mEmail = (TextView)findViewById(R.id.email);
        myourEmail = (TextView)findViewById(R.id.yourEmail);
        mChangePassword = (Button)findViewById(R.id.changePassword);
        mEmail.setText(mAuth.getCurrentUser().getEmail());
        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null) {
                    mAuth.sendPasswordResetEmail(mAuth.getCurrentUser().getEmail());
                    mChangePassword.setVisibility(View.INVISIBLE);
                    mEmail.setVisibility(View.INVISIBLE);
                    myourEmail.setText("Check your email to reset your password");
                    mAuth.signOut();
                }

            }
        });
    }
}
