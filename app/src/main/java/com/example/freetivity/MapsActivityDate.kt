package com.example.freetivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivityDate : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment1 = supportFragmentManager
                .findFragmentById(R.id.map1) as SupportMapFragment
        mapFragment1.getMapAsync(this)

        val profileButton = findViewById(R.id.ProfileButton) as ImageView
        // set on-click listener
        profileButton.setOnClickListener {
            val profile = Intent(this,Profile::class.java)
            startActivity(profile)
        }

        val logoutButton = findViewById(R.id.LogoutButton) as ImageView
        // set on-click listener
        logoutButton.setOnClickListener {
            val logout = Intent(this,LoginActivity::class.java)
            startActivity(logout)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //set coordinates to activity locations and add markers
        val Signature = LatLng(41.8988, -87.623)
        mMap.addMarker(MarkerOptions().position(Signature).title("The Signature Lounge at the 96th"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Signature, 10f))

        val Lakefront = LatLng(41.907484, -87.625039)
        mMap.addMarker(MarkerOptions().position(Lakefront).title("Lakefront Trail"))

        val Summerdance = LatLng(41.876465, -87.621887)
        mMap.addMarker(MarkerOptions().position(Summerdance).title("Chicago SummerDance"))

        val Lincoln = LatLng(41.9242, -87.6353)
        mMap.addMarker(MarkerOptions().position(Lincoln).title("Lincoln Park Conservatory"))

    }
}