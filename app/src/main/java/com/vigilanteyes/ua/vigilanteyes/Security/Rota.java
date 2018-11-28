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

import com.vigilanteyes.ua.vigilanteyes.LockableViewPager;
import com.vigilanteyes.ua.vigilanteyes.LoginScreen;
import com.vigilanteyes.ua.vigilanteyes.R;
import com.vigilanteyes.ua.vigilanteyes.SectionStatePageAdapter;

public class Rota extends AppCompatActivity {

    private SectionStatePageAdapter mSectionStatePageAdapter;
    private LockableViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota);

        mSectionStatePageAdapter = new SectionStatePageAdapter(getSupportFragmentManager());

        mViewPager = (LockableViewPager) findViewById(R.id.rotaContainer);

        setupViewPager(mViewPager);
        this.setViewPager(0);
    }

    private void setupViewPager(ViewPager vp){
        mSectionStatePageAdapter.addFragment(new RotaCheckIn());
        mSectionStatePageAdapter.addFragment(new RotaCheckpoints());
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

            case R.id.logout:
                startActivity(new Intent(this, LoginScreen.class));
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
