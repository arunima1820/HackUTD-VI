package com.example.cometevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
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
        //readAllEvents();
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
                    String eventNameStr = document.toString();

                    //String  = (String) document.get("Organizer");
                    String locationNameStr = (String) document.get("LocationName");
                    String descriptioStrn = (String) document.get("Description");
                    String timeStr = (String) document.get("Time");

                    TextView eventNameView = (TextView) findViewById(R.id.eventNameID);
                    TextView eventLocation = (TextView) findViewById( R.id.eventLocationID);
                    TextView eventDescription = (TextView) findViewById(R.id.eventDescription);
                    TextView date = (TextView) findViewById(R.id.eventDateID);


                    Resources res =  getResources();
                    String text = String.format(res.getString(R.string.event_name), eventNameStr);

                    eventNameView.setText(res.getString((R.string.event_name), eventNameStr));
                    eventLocation.setText(locationNameStr);
                    eventDescription.setText(descriptioStrn);
                    date.setText(timeStr);
                    Toast.makeText(DetailActivity.this,eventNameView.getText(),Toast.LENGTH_SHORT).show();
                }}
                }
            }
        );
    }
}
