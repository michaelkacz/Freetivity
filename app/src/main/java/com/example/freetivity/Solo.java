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

public class Solo extends AppCompatActivity {
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
        setContentView(R.layout.activity_solo);

        map1 = getSupportFragmentManager().findFragmentById(R.id.map1);

        //hide map fragment initially when opening category
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(map1);
        fragmentTransaction.commit();

        TextActivity1 = (ListView) findViewById(R.id.activity1);

        //create array and adapter to house activities
        final ArrayList<String> ActivityList = new ArrayList<>();
        final ArrayAdapter ActivityAdapter = new ArrayAdapter<String>(this, R.layout.list, ActivityList);
        TextActivity1.setAdapter(ActivityAdapter);

        //set reference to child "Solo" in firebase DB
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Solo");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //clear the array
                ActivityList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //grab getters and setters from ActivityInfo
                    ActivityInfo solo1 = snapshot.getValue(ActivityInfo.class);

                    //set list view string to display activity name, location, and info
                    String solo1info = "Activity Name: " + solo1.getActivityName() + "\n" +
                            "Location: " + solo1.getActivityLocation() + "\n" + "\n" + "Description: " + solo1.getActivityDesc();
                    ActivityList.add(solo1info);
                }
                ActivityAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Solo.this, "Error. No activities. Check back later!", Toast.LENGTH_LONG).show();            }
        });

        //set button to logout user
        Logout = (ImageView) findViewById(R.id.LogoutButton);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                openLoginActivity();
            }
        });

        //send user to profile activity
        Profile = (ImageView) findViewById(R.id.ProfileButton);
        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });

        //show map fragment when button is clicked
        mapButton1 = (ImageView) findViewById(R.id.mapButton1);
        mapButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(map1);
                fragmentTransaction.commit();
                mapButton1.setVisibility(View.INVISIBLE);
                openMapActivity();
            }
        });
    }

    //intent to open login activity when user logs out
    public void openLoginActivity() {
        Intent openActivity = new Intent(this, LoginActivity.class);
        startActivity(openActivity);
    }

    //intent to open profile activity
    public void openProfileActivity() {
        Intent openActivity = new Intent(this, Profile.class);
        startActivity(openActivity);
    }

    //intent to open Kotlin map file when maps button is clicked
    public void openMapActivity() {
        Intent openActivity = new Intent(this, MapsActivitySolo.class);
        startActivity(openActivity);
    }
}
