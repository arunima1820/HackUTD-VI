package com.example.cometevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageButton backToMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        backToMap = findViewById(R.id.mapbutton);
        backToMap.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  Intent intToAddActivity = new Intent(getBaseContext(),HomeActivity.class);
                  startActivity(intToAddActivity);
              }
          }
        );

        //readAllEvents();

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
                    String eventName = document.toString();
                    String organizer = (String) document.get("Organizer");
                    String locationName = (String) document.get("LocationName");
                    String description = (String) document.get("Description");
                    String time = (String) document.get("Time");

                    TextView eventNameView = findViewById(R.id.eventNameID);
                    TextView eventLocation = findViewById(R.id.eventLocationID);
                    TextView eventDescription = findViewById(R.id.eventDescription);
                    TextView date = findViewById(R.id.eventDateID);

                    eventNameView.setText(eventName);
                    eventLocation.setText(locationName);
                    eventDescription.setText(description);
                    date.setText(time);
                }}
                }
            }
        );
    }
}
