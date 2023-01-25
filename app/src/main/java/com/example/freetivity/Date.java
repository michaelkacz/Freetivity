package com.example.freetivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Date extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ListView TextActivity1;
    private ImageView Logout, Profile;
    private Fragment map1;
    ImageView mapButton1;

    //database location:
        //https://console.firebase.google.com/u/1/project/login-d4af6/database/login-d4af6-default-rtdb/data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        map1 = getSupportFragmentManager().findFragmentById(R.id.map1);

        //set map fragment to be hidden upon opening Date category
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(map1);
        fragmentTransaction.commit();

        TextActivity1 = (ListView) findViewById(R.id.activity1);

        //create array and adapter to house activity data
        final ArrayList<String> ActivityList = new ArrayList<>();
        final ArrayAdapter ActivityAdapter = new ArrayAdapter<String>(this, R.layout.list, ActivityList);
        TextActivity1.setAdapter(ActivityAdapter);

        //set reference to firebase DB on child "Date"
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Date");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ActivityList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //grab getters and setters from ActivityInfo class
                    ActivityInfo date1 = snapshot.getValue(ActivityInfo.class);
                    //set string to show activity name, location, and info and bind to array
                    String solo1info = "Activity Name: " + date1.getActivityName() + "\n" +
                            "Location: " + date1.getActivityLocation() + "\n" + "\n" + "Description: " + date1.getActivityDesc();
                    ActivityList.add(solo1info);
                }
                ActivityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Date.this, "Error. No activities. Check back later!", Toast.LENGTH_LONG).show();            }
        });

        //logout button logs out the user
        Logout = (ImageView) findViewById(R.id.LogoutButton);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                openLoginActivity();
            }
        });

        //send user to profile activity when clicked
        Profile = (ImageView) findViewById(R.id.ProfileButton);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        //open map fragment when button is clicked
        mapButton1 = (ImageView) findViewById(R.id.mapButton1);
        mapButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(map1);
                fragmentTransaction.commit();
                openMapActivity();
            }
        });
    }

    //intent to open login activity when user logs out
    public void openLoginActivity() {
        Intent openActivity = new Intent(this, LoginActivity.class);
        startActivity(openActivity);
    }

    //intent to open profile activity when clicked on profile image
    public void openProfileActivity() {
        Intent openActivity = new Intent(this, Profile.class);
        startActivity(openActivity);
    }

    //intent to open Kotlin map file when maps button is clicked
    public void openMapActivity() {
        Intent openActivity = new Intent(this, MapsActivityDate.class);
        startActivity(openActivity);
    }
}
