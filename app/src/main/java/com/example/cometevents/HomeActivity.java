package com.example.cometevents;
//package com.example.aoc.googlemapdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cometevents.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SupportMapFragment mapFragment  = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap){
        //Toast.makeText(HomeActivity.this,"OnMapReady running!",Toast.LENGTH_SHORT).show();
        float zoomLevel = 16;
        map = googleMap;
        LatLng utd = new LatLng(32.985774, -96.750990);
        map.addMarker(new MarkerOptions().position(utd).title("UTD"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(utd,zoomLevel));

        GroundOverlayOptions UTDMap = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.utdmap)).position(utd,2160f,2000f);

        map.addGroundOverlay(UTDMap);
    }
}
