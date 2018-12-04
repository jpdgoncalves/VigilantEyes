package com.vigilanteyes.ua.vigilanteyes.Security;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vigilanteyes.ua.vigilanteyes.LoginScreen;
import com.vigilanteyes.ua.vigilanteyes.R;

public class HomeSecurity extends AppCompatActivity {

    private Button btnRota;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mWorksheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_security);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        btnRota = (Button) findViewById(R.id.btn_rota);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser == null) {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            finish();
        }
    }

    public void rotaBtnPressed(View view) {
        mWorksheet = mDatabase.getReference("worksheets").child(mCurrentUser.getUid());
        mWorksheet.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("status").getValue().toString();
                if(status.equals("finished")){
                    Toast.makeText(HomeSecurity.this,"Não há roats disponíveis!",Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(HomeSecurity.this,Rota.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                        mAuth.signOut();
                        Intent intent = new Intent(getApplicationContext(),LoginScreen.class);
                        startActivity(intent);
                    }
                });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();


    }
}
