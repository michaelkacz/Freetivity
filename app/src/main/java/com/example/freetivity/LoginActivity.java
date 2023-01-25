package com.example.freetivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    Button CancelButton;
    Button Login;
    private TextView RegButton;
    EditText EmailText;
    protected EditText PasswordText;
    private FirebaseAuth mAuth;

    //database location:
    //https://console.firebase.google.com/u/1/project/login-d4af6/database/login-d4af6-default-rtdb/data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Freetivity);
        setContentView(R.layout.activity_login);
        //mapping all buttons and edittext's to corresponding ID's
        RegButton = (TextView) findViewById(R.id.RegButton);
        EmailText = (EditText) findViewById(R.id.EmailText);
        PasswordText = (EditText) findViewById(R.id.PasswordText);

        mAuth = FirebaseAuth.getInstance();

        //onclick listener for login submit button. runs through validations
        Login = (Button) findViewById(R.id.SubmitButton);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sets email and password to string and trims
                String UserEmail = EmailText.getText().toString().trim();
                String UserPass = PasswordText.getText().toString().trim();
                //requires email
                if(UserEmail.isEmpty()) {
                    EmailText.setError("Email is required!");
                    EmailText.requestFocus();
                    return;
                }
                //validates if provided email is a true email pattern
                if(!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()){
                    EmailText.setError("Must provide valid Email!");
                    EmailText.requestFocus();
                    return;
                }
                //validates password is not empty
                if(UserPass.isEmpty()) {
                    PasswordText.setError("Password is required!");
                    PasswordText.requestFocus();
                    return;
                }
                //makes sure user password is more than 6 characters
                if(UserPass.length() < 6) {
                    PasswordText.setError("Password must be 6 or more characters!");
                    PasswordText.requestFocus();
                    return;
                }

                //firebase authentication with email and password
                mAuth.signInWithEmailAndPassword(UserEmail, UserPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //open main category activity if login is successful
                            openMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed. Please check credentials.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //clears all text in email and password on cancel button click
        CancelButton = (Button) findViewById(R.id.CancelButton);
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordText = (EditText) findViewById(R.id.PasswordText);
                EmailText = (EditText) findViewById(R.id.EmailText);
                EmailText.setText("");
                PasswordText.setText("");
            }
        });

        //switch case to send user to register activity when "Register" textview is clicked
        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.RegButton:
                    openRegisterActivity();
                    break;
                }
            }
        });
    }

    //intent to open main activity
    public void openMainActivity() {
        Intent openActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(openActivity);
    }

    //intent to open register activity
    public void openRegisterActivity() {
        Intent openActivity = new Intent(this, RegisterActivity.class);
        startActivity(openActivity);
    }
}
