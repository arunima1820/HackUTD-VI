package com.example.cometevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class calendarActivity extends AppCompatActivity {

    ImageButton mapbtn;
    ImageButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mapbtn = findViewById(R.id.mapbutton);
        addBtn = findViewById(R.id.add);

        mapbtn.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  Intent intToAddActivity = new Intent(getBaseContext(),HomeActivity.class);
                  startActivity(intToAddActivity);
              }
          }
        );

        addBtn.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View v) {
                  Intent intToAddActivity = new Intent(getBaseContext(),AddActivity.class);
                  startActivity(intToAddActivity);
              }
          }
        );
    }
}
