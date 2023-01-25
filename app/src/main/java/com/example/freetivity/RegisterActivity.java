package com.example.freetivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button ClearButton;
    Button RegisterButton;
    Button CancelButton1;
    EditText FullNameText;
    EditText RegEmailText;
    protected EditText RegPasswordText;

    //database location:
    //https://console.firebase.google.com/u/1/project/login-d4af6/database/login-d4af6-default-rtdb/data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //get instance of firebase authentication
        mAuth = FirebaseAuth.getInstance();
        //set corresponding buttons and text fields to ID's
            ClearButton = (Button) findViewById(R.id.ClearButton);
            CancelButton1 = (Button) findViewById(R.id.CancelButton1);
            RegisterButton = (Button) findViewById(R.id.RegisterButton);

            FullNameText = (EditText) findViewById(R.id.FullNameText);
            RegEmailText = (EditText) findViewById(R.id.RegEmailText);
            RegPasswordText = (EditText) findViewById(R.id.RegPasswordText);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FullName = FullNameText.getText().toString().trim();
                String UserEmail = RegEmailText.getText().toString().trim();
                String UserPass = RegPasswordText.getText().toString().trim();

                //email and password validation for registration
                if(FullName.isEmpty()) {
                    FullNameText.setError("Must provide full name!");
                    FullNameText.requestFocus();
                    return;
                }
                if(UserEmail.isEmpty()) {
                    RegEmailText.setError("Must provide Email!");
                    RegEmailText.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(UserEmail).matches()){
                    RegEmailText.setError("Must provide valid Email!");
                    RegEmailText.requestFocus();
                    return;
                }
                if(UserPass.isEmpty()) {
                    RegPasswordText.setError("Must provide password!");
                    RegPasswordText.requestFocus();
                    return;
                }
                if(UserPass.length() < 6) {
                    RegPasswordText.setError("Password must be 6 or more characters!");
                    RegPasswordText.requestFocus();
                    return;
                }

                //authenticate user registration through firebase
                mAuth.createUserWithEmailAndPassword(UserEmail, UserPass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Member user = new Member(FullName, UserEmail);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Member registered!", Toast.LENGTH_LONG).show();
                                                openMainActivity();
                                            } else {
                                                Toast.makeText(RegisterActivity.this, "Member registration failed!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Member registration failed!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        //listener to clear information
        ClearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FullNameText = (EditText) findViewById(R.id.FullNameText);
                    RegEmailText = (EditText) findViewById(R.id.RegEmailText);
                    RegPasswordText = (EditText) findViewById(R.id.RegPasswordText);
                    RegEmailText.setText("");
                    RegPasswordText.setText("");
                    FullNameText.setText("");
                }
            });
        //listener to send user back to login
        CancelButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity();
            }
        });
        }

    //intent to send user to main activity
    public void openMainActivity() {
        Intent openActivity = new Intent(this, MainActivity.class);
        startActivity(openActivity);
    }

    //intent to send user to login activity
    public void openLoginActivity() {
        Intent openActivity = new Intent(this, LoginActivity.class);
        startActivity(openActivity);
    }
    }
