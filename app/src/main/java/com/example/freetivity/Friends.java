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

public class Friends extends AppCompatActivity {
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
        setContentView(R.layout.activity_friends);

        //grab fragment map from XML file
        map1 = getSupportFragmentManager().findFragmentById(R.id.map1);

        //hide map initially when activity loads
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(map1);
        fragmentTransaction.commit();

        TextActivity1 = (ListView) findViewById(R.id.activity1);

        //create array list and adapter. set adapter to listview declared above
        final ArrayList<String> ActivityList = new ArrayList<>();
        final ArrayAdapter ActivityAdapter = new ArrayAdapter<String>(this, R.layout.list, ActivityList);
        TextActivity1.setAdapter(ActivityAdapter);

        //reference firebase database child "Friends" to grab data from there
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Friends");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear the activity list
                ActivityList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //get value of corresponding activity information from getters/setters in
                    //activity info class
                    ActivityInfo friends1 = snapshot.getValue(ActivityInfo.class);
                    //bind activity name, location, and info to string
                    //add the string to listview
                    String solo1info = "Activity Name: " + friends1.getActivityName() + "\n" +
                            "Location: " + friends1.getActivityLocation() + "\n" + "\n" + "Description: " + friends1.getActivityDesc();
                    ActivityList.add(solo1info);
                }
                ActivityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Friends.this, "Error. No activities. Check back later!", Toast.LENGTH_LONG).show();
            }
        });

        //set onclick event to logout button
        Logout = (ImageView) findViewById(R.id.LogoutButton);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                openLoginActivity();
            }
        });

        //set onclick event to profile button
        Profile = (ImageView) findViewById(R.id.ProfileButton);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        //display map fragment when map button is clicked
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

    //intent to log user out and send back to login activity
    public void openLoginActivity() {
        Intent openActivity = new Intent(this, LoginActivity.class);
        startActivity(openActivity);
    }

    //intent to send user to profile activity
    public void openProfileActivity() {
        Intent openActivity = new Intent(this, Profile.class);
        startActivity(openActivity);
    }

    //intent to open Kotlin map file to show coordinates
    public void openMapActivity() {
        Intent openActivity = new Intent(this, MapsActivityFriends.class);
        startActivity(openActivity);
    }
}
