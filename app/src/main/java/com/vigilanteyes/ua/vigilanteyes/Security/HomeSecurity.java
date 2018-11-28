package com.vigilanteyes.ua.vigilanteyes.Security;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.vigilanteyes.ua.vigilanteyes.LoginScreen;
import com.vigilanteyes.ua.vigilanteyes.R;

public class HomeSecurity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_security);
    }

    public void rotaBtnPressed(View view) {
        Intent intent = new Intent(this,Rota.class);
        startActivity(intent);
    }

    public void reportarBtnPressed(View view) {
        Intent intent = new Intent(this,Report.class);
        startActivity(intent);
    }

    public void perfilBtnPressed(View view) {
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }

    public void logoutBtnPressed(View view) {
        Intent intent = new Intent(this,LoginScreen.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.logout:
                startActivity(new Intent(this,LoginScreen.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
