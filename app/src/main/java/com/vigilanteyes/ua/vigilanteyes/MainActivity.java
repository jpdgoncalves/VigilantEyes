package com.vigilanteyes.ua.vigilanteyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vigilanteyes.ua.vigilanteyes.Security.HomeSecurity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCurrentUser = mAuth.getCurrentUser();
    }

    public void toLoginScreen(View view) {
        if (mCurrentUser != null) {
            Intent in = new Intent(this, HomeSecurity.class);
            startActivity(in);
            finish();
        } else {
            Intent in = new Intent(this, LoginScreen.class);
            startActivity(in);
            finish();
        }
    }
}
