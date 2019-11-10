package com.example.cometevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class DetailActivity extends AppCompatActivity {

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

    }
}
