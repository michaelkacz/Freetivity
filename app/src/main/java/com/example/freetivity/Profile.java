package com.example.freetivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private FirebaseUser fbuser;
    private DatabaseReference dbreference;
    private String user;
    private ImageView Logout;

    //database location:
    //https://console.firebase.google.com/u/1/project/login-d4af6/database/login-d4af6-default-rtdb/data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //grabs current users information
        fbuser = FirebaseAuth.getInstance().getCurrentUser();
        //set reference to "Users" data in DB
        dbreference = FirebaseDatabase.getInstance().getReference("Users");
        //grabs the unique user ID
        user = fbuser.getUid();
        final TextView TextUserEmail = (TextView) findViewById(R.id.ProfileEmail);
        final TextView TextUserName = (TextView) findViewById(R.id.ProfileName);

        dbreference.child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Member memberprofile = snapshot.getValue(Member.class);

                //if the user is logged in, display name and email and set them to corresponding
                //text view's
                if (memberprofile != null) {
                    String UserName = memberprofile.FullName;
                    String UserEmail = memberprofile.Email;

                    TextUserName.setText("Full Name:  " + UserName);
                    TextUserEmail.setText("Email:  " + UserEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

        //bind logout button to imageview
        Logout = (ImageView) findViewById(R.id.LogoutButton);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                openLoginActivity();
            }
        });
    }

    //intent to open login activity when user logs out
    public void openLoginActivity() {
        Intent openActivity = new Intent(this, LoginActivity.class);
        startActivity(openActivity);
    }

}
