package com.example.freetivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ImageView Logout, Profile;
    TextView Solo, Date, Friends;

    //database location:
    //https://console.firebase.google.com/u/1/project/login-d4af6/database/login-d4af6-default-rtdb/data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set profile button to imageview in XML and add click event
        Profile = (ImageView) findViewById(R.id.ProfileButton);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        //set logout button to imageview in XML and add click event
        Logout = (ImageView) findViewById(R.id.LogoutButton);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                openLoginActivity();
            }
        });

        //set solo textview to imageview in XML and add click event
        Solo = (TextView) findViewById(R.id.textView1);
        Solo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSoloActivity();
            }
        });

        //set date textview to imageview in XML and add click event
        Date = (TextView) findViewById(R.id.textView2);
        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDateActivity();
            }
        });

        //set friends textview to imageview in XML and add click event
        Friends = (TextView) findViewById(R.id.textView3);
        Friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFriendsActivity();
            }
        });
    }

    //intent to open solo activity when solo text view is clicked
    public void openSoloActivity() {
        Intent openActivity = new Intent(this, Solo.class);
        startActivity(openActivity);
    }

    //intent to open date activity when date text view is clicked
    public void openDateActivity() {
        Intent openActivity = new Intent(this, Date.class);
        startActivity(openActivity);
    }

    //intent to open friends activity when friends text view is clicked
    public void openFriendsActivity() {
        Intent openActivity = new Intent(this, Friends.class);
        startActivity(openActivity);
    }

    //intent to log out user and send them back to login activity
    public void openLoginActivity() {
        Intent openActivity = new Intent(this, LoginActivity.class);
        startActivity(openActivity);
    }

    //intent to open proile activity when profile imageview is clicked
    public void openProfileActivity() {
        Intent openActivity = new Intent(this, Profile.class);
        startActivity(openActivity);
    }
}