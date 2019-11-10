package com.example.cometevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AddActivity extends AppCompatActivity {

    ImageButton mapBtn;
    ImageButton calBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mapBtn = findViewById(R.id.mapbutton);
        calBtn = findViewById(R.id.calendar);
        mapBtn.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  Intent intToAddActivity = new Intent(getBaseContext(),HomeActivity.class);
                  startActivity(intToAddActivity);
              }
          }
        );

        calBtn.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  Intent intToAddActivity = new Intent(getBaseContext(),calendarActivity.class);
                  startActivity(intToAddActivity);
              }
          }
        );
    }
}
