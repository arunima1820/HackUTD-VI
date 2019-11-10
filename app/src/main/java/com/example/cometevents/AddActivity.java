package com.example.cometevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firestore.v1.DocumentTransform;
import com.google.type.LatLng;

import java.security.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    ImageButton mapBtn;
    ImageButton calBtn;
    Button submitBtn;

    Map<String, GeoPoint> getLatLong = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mapBtn = findViewById(R.id.mapbutton);
        calBtn = findViewById(R.id.calendar);
        submitBtn = findViewById(R.id.addEvent);

        getLatLong.put("ECSW", new GeoPoint(32.986390, -96.751416));
        getLatLong.put("ECSS", new GeoPoint(32.986549, -96.750266));
        getLatLong.put("ECSN", new GeoPoint(32.986982, -96.750378));
        getLatLong.put("HH", new GeoPoint(32.987063, -96.751516));
        getLatLong.put("JSOM", new GeoPoint(32.985344, -96.747000));
        getLatLong.put("FO", new GeoPoint(32.987654, -96.749083));
        getLatLong.put("FN", new GeoPoint(32.988315, -96.749504));
        getLatLong.put("MC", new GeoPoint(32.987085, -96.747656));
        getLatLong.put("SSA", new GeoPoint(32.986267, -96.749404));
        getLatLong.put("SSB", new GeoPoint(32.985916, -96.748833));
        getLatLong.put("VCB", new GeoPoint(32.984599, -96.749410));
        getLatLong.put("ATEC", new GeoPoint(32.986212, -96.747593));
        getLatLong.put("SLC", new GeoPoint(32.988169, -96.750372));

        mapBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intToAddActivity = new Intent(getBaseContext(), HomeActivity.class);
                  startActivity(intToAddActivity);
              }
          }
        );

        calBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intToAddActivity = new Intent(getBaseContext(), calendarActivity.class);
                  startActivity(intToAddActivity);
              }
          }
        );

        submitBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 EditText nameEdit = (EditText) findViewById(R.id.inputName);
                 String eventName = nameEdit.getText().toString();
                 //EditText dateEdit = (EditText) findViewById(R.id.inputDate);
                 //String eventDate = dateEdit.getText().toString();
                 EditText locationEdit = (EditText) findViewById(R.id.inputLocation);
                 String eventLocation = locationEdit.getText().toString();
                 //EditText timeEdit = (EditText) findViewById(R.id.inputTime);
                 //String eventTime = timeEdit.getText().toString();
                 addNewContact(eventName, eventLocation);
                 Intent intToHome = new Intent(AddActivity.this,HomeActivity.class);
                 startActivity(intToHome);
             }

         }
        );
    }

    private void addNewContact(String name,String location) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> newContact = new HashMap<>();
        newContact.put("Description", "No description found");
        //Toast.makeText(AddActivity.this,"Location: "+ location,Toast.LENGTH_SHORT).show();
        newContact.put("Location", getLatLong.get(location));
        newContact.put("LocationName",location);
        newContact.put("Time", "November 20, 2019 at 2:00 PM UTC-6");
        db.collection("Events").document(name).set(newContact)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddActivity.this, "Added successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "ERROR" + e.toString(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }
                });
    }
}
