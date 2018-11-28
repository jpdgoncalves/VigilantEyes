package com.vigilanteyes.ua.vigilanteyes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.vigilanteyes.ua.vigilanteyes.Security.HomeSecurity;
import com.vigilanteyes.ua.vigilanteyes.Security.Profile;
import com.vigilanteyes.ua.vigilanteyes.Security.Rota;

import java.security.Security;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void loginBtnPressed(View view) {
        Intent intent = new Intent(this, HomeSecurity.class);
        startActivity(intent);
    }
}
