package com.vigilanteyes.ua.vigilanteyes.Security;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vigilanteyes.ua.vigilanteyes.LoginScreen;
import com.vigilanteyes.ua.vigilanteyes.R;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseDatabase mDatabase;
    private StorageReference mPfp;
    private DatabaseReference mUser;

    private ImageView profileImage;
    private TextView userName;
    private TextView userIdade;
    private TextView userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = (ImageView) findViewById(R.id.profile_picture);
        userName = (TextView) findViewById(R.id.name_field);
        userIdade = (TextView) findViewById(R.id.age_field);
        userEmail = (TextView) findViewById(R.id.email_field);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mCurrentUser = mAuth.getCurrentUser();
        if(mCurrentUser == null) {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            finish();
        } else {
            mDatabase = FirebaseDatabase.getInstance();
            mUser = mDatabase.getReference("users").child(mCurrentUser.getUid());
            mUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String age = dataSnapshot.child("idade").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String photoURL = dataSnapshot.child("photoURL").getValue().toString();
                    Profile.this.setupProfile(name,age,email,photoURL);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void setupProfile(String name,String age,String email,String photoURL) {
        userName.setText(name);
        userIdade.setText(age);
        userEmail.setText(email);
        mPfp = FirebaseStorage.getInstance().getReferenceFromUrl(photoURL);
        Log.d("DEBUGGING",photoURL);
        Glide.with(this).load(mPfp).into(profileImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this,HomeSecurity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
