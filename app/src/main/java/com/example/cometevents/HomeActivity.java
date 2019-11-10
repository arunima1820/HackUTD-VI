package com.example.cometevents;
//package com.example.aoc.googlemapdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cometevents.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.value.GeoPointValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback {
    //private static final String TAG = MainActivity.class.getSimpleName();
    // Access a Cloud Firestore instance from your Activity
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GoogleMap mapView;
    Map<String, Object> user = new HashMap<>();
    ImageButton calBtn;
    ImageButton addEventBtn;
    ImageButton mapBtn;

    //HashMap<String,Marker> markers = new HashMap<>();
    //ArrayList<Marker> markers = new ArrayList<>();
    //Marker lastClickedMarker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SupportMapFragment mapFragment  = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        calBtn = findViewById(R.id.calendar);
        addEventBtn = findViewById(R.id.add);
        mapBtn = findViewById(R.id.mapbutton);
        readAllEvents();

        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this,"Working",Toast.LENGTH_SHORT).show();
                Intent intToHome = new Intent(HomeActivity.this, calendarActivity.class);
                startActivity(intToHome);
            }
        });

        addEventBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intToAddActivity = new Intent(HomeActivity.this, AddActivity.class);
                startActivity(intToAddActivity);
            }
                                       }
        );

        mapBtn.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   Intent intToAddActivity = new Intent(getBaseContext(),HomeActivity.class);
                   startActivity(intToAddActivity);
               }
           }
        );
    }

    public void onMapReady(GoogleMap googleMap){
        //Toast.makeText(HomeActivity.this,"OnMapReady running!",Toast.LENGTH_SHORT).show();
        float zoomLevel = 16;
        mapView = googleMap;
        //map.setOnMarkerClickListener(this);
        mapView.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent toMoreDetail = new Intent(HomeActivity.this, DetailActivity.class);
                startActivity(toMoreDetail);
                //Toast.makeText(HomeActivity.this,"Show more detail about " + marker.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });
        LatLng utd = new LatLng(32.985774, -96.750990);
        //map.addMarker(new MarkerOptions().position(utd).title("UTD"));
        mapView.moveCamera(CameraUpdateFactory.newLatLngZoom(utd,zoomLevel));

        GroundOverlayOptions UTDMap = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.utdmap)).position(utd,2160f,2000f);

        mapView.addGroundOverlay(UTDMap);
    }

    /*@Override
    public boolean onMarkerClick(final Marker marker) {
        if (lastClickedMarker != null) {
            if (lastClickedMarker == marker) {
                Toast.makeText(HomeActivity.this,"Show more detail about " + marker.getTitle(),Toast.LENGTH_SHORT).show();
            } else {
                marker.showInfoWindow();
            }
        }
        lastClickedMarker = marker;
        return true;
    }*/

    private void addNewContact() {
        Map<String, Object> newContact = new HashMap<>();
        newContact.put("Name", "John");
        newContact.put("Description", "john@gmail.com");
        newContact.put("Location", "080-0808-009");
        db.collection("Events").document("NewEvents!").set(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(HomeActivity.this, "User Registered",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HomeActivity.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        //Log.d("TAG", e.toString());
                    }
                });
    }

    private void readAllEvents(){
        db.collection("Events").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        GeoPoint g = (GeoPoint) document.get("Location");
                        double x = g.getLatitude();
                        double y = g.getLongitude();
                        String locationName = (String) document.get("LocationName");
                        String description = (String) document.get("Description");
                        //Toast.makeText(HomeActivity.this,document.getId()+ " x:"  + x + " y:" + y,
                        //        Toast.LENGTH_SHORT).show();
                        Log.d(locationName,x + " " + y + " desc:" + description);
                        createMarker(x,y, locationName,description);
                    }}else{
                        Toast.makeText(HomeActivity.this,"Unable to get data from the files!",Toast.LENGTH_SHORT).show();
                 }
                }
            }
        );
    }

    public  Marker createMarker(double latitude, double longitude, String title,String desc){
        Marker m = mapView.addMarker(new MarkerOptions()
        .position(new LatLng(latitude,longitude)).anchor(0.5f,0.5f).title(title).snippet(desc));

        //markers.add(m);

        return m;
    }
}
