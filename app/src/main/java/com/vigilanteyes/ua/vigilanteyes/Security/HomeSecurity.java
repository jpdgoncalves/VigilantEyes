package com.vigilanteyes.ua.vigilanteyes.Security;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.DialogStyle);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Tem a certeza que pretende sair de VigilantEyes?")
                .setTitle("Logout")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
                        startActivity(intent);
                    }
                });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();


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
