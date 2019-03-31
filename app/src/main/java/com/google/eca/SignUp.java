package com.google.eca;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputName,inputYear,inputSec,inputPhone;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private FirebaseAuth auth;
    private DatabaseReference mRef;
    FirebaseUser user;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
//        mRef = FirebaseDatabase.getInstance().getReference();
//        user = FirebaseAuth.getInstance().getCurrentUser();

        inputEmail = findViewById(R.id.email_signup);
        inputPassword = findViewById(R.id.password_signup);
        inputName = findViewById(R.id.name_signup);
        inputYear = findViewById(R.id.year_signup);
        inputSec = findViewById(R.id.section_signup);
        inputPhone = findViewById(R.id.phone_signup);

        btnResetPassword = findViewById(R.id.resetButton_signup);
        btnSignIn = findViewById(R.id.login_button_signup);
        btnSignUp = findViewById(R.id.signup_button);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,Reset.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //btnSignUp.setVisibility(View.INVISIBLE);

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String name = inputName.getText().toString().trim();
                final String year = inputYear.getText().toString().trim();
                final String sec = inputSec.getText().toString().trim();
                final String phone = "+91"+inputPhone.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(year)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(sec)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //uid = user.getUid();

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    mRef = FirebaseDatabase.getInstance().getReference();
                                    user = FirebaseAuth.getInstance().getCurrentUser();

                                    uid = user.getUid();

                                    UserDetails ud = new UserDetails(name,year,sec,phone,email);

                                    mRef.child("Users").child(uid).setValue(ud);

                                    startActivity(new Intent(SignUp.this, Activities.class));
                                    finish();
                                }
                            }
                        });

            }
        });

    }

}

