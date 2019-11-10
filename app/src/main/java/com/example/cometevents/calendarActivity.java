package com.example.cometevents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class calendarActivity extends AppCompatActivity {

    ImageButton mapbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mapbtn = findViewById(R.id.map);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMap = new Intent(calendarActivity.this,HomeActivity.class);
                startActivity(toMap);
            }
        });
    }
}
