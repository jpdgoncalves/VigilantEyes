package com.vigilanteyes.ua.vigilanteyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vigilanteyes.ua.vigilanteyes.Security.Profile;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void loginBtnPressed(View view) {
        Intent in = new Intent(this, Profile.class);
        startActivity(in);
    }
}
