package com.vigilanteyes.ua.vigilanteyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toLoginScreen(View view) {
        Intent in = new Intent(this, LoginScreen.class);
        startActivity(in);
    }
}
