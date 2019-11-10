package com.example.cometevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button loginButton;
    FirebaseAuth mFirebaseAuth;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);

        loginButton = findViewById(R.id.loginBtn);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(MainActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"Failed to log in",Toast.LENGTH_SHORT).show();
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acquiredEmail = email.getText().toString();
                String pwd = password.getText().toString();
                if (acquiredEmail.isEmpty()) {
                    email.setError("Please enter email address");
                    email.requestFocus();
                } else if (pwd.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                } else if(acquiredEmail.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Both fields are empty!",Toast.LENGTH_SHORT).show();
                } else if (!acquiredEmail.isEmpty() && !pwd.isEmpty()){
                    mFirebaseAuth.signInWithEmailAndPassword(acquiredEmail, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Login Error, Please Login",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
                                //startActivity(intToHome);
                                Toast.makeText(MainActivity.this,"Logged in!",Toast.LENGTH_SHORT).show();
                                //password.setError("logged in!");
                                //password.requestFocus();
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this,"Unknown error!",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}