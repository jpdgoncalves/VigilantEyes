package com.vigilanteyes.ua.vigilanteyes.Security;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vigilanteyes.ua.vigilanteyes.LockableViewPager;
import com.vigilanteyes.ua.vigilanteyes.LoginScreen;
import com.vigilanteyes.ua.vigilanteyes.R;
import com.vigilanteyes.ua.vigilanteyes.SectionStatePageAdapter;

public class Rota extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mWorkSheet;

    private SectionStatePageAdapter mSectionStatePageAdapter;
    private LockableViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        mSectionStatePageAdapter = new SectionStatePageAdapter(getSupportFragmentManager());

        mViewPager = (LockableViewPager) findViewById(R.id.rotaContainer);

        setupViewPager(mViewPager);
        updateViewPager();
    }

    private void updateViewPager() {
        mWorkSheet = mDatabase.getReference("worksheets").child(mCurrentUser.getUid()).child("status");
        mWorkSheet.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String status = dataSnapshot.getValue().toString();
                if(status.equals("planned")) {
                    Rota.this.setViewPager(0);
                } else if (status.equals("active")) {
                    Rota.this.setViewPager(1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCurrentUser = mAuth.getCurrentUser();
        if(mCurrentUser == null) {
            Intent intent = new Intent(this, LoginScreen.class);
            startActivity(intent);
            finish();
        }
    }

    private void setupViewPager(ViewPager vp){
        mSectionStatePageAdapter.addFragment(new RotaCheckIn());
        mSectionStatePageAdapter.addFragment(new RotaCheckpoints());
        mSectionStatePageAdapter.addFragment(new RotaCheckOut());
        vp.setAdapter(mSectionStatePageAdapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this,HomeSecurity.class));
                return true;
        }


        return super.onOptionsItemSelected(item);

    }
}
