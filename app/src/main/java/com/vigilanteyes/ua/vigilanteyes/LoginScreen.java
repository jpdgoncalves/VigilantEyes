package com.vigilanteyes.ua.vigilanteyes;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.vigilanteyes.ua.vigilanteyes.Security.HomeSecurity;
import com.vigilanteyes.ua.vigilanteyes.Security.Profile;
import com.vigilanteyes.ua.vigilanteyes.Security.Rota;

import java.security.Security;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText emailInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        emailInput = (EditText) findViewById(R.id.user_text);
        passwordInput = (EditText) findViewById(R.id.pass_text);
        mAuth = FirebaseAuth.getInstance();
    }

    public void loginBtnPressed(View view) {

        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if(email.length() == 0 || password.length() == 0) {
            Toast.makeText(this, "Please fill both input fields!",Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(LoginScreen.this,HomeSecurity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginScreen.this, "The email or password are wrong!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
