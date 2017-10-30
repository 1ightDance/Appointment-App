package com.example.lightdance.appointment.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.fragments.BrowseFragment;
import com.example.lightdance.appointment.fragments.NewAppointmentFragment;

public class BrowserActivity extends AppCompatActivity {

    private int mCurrentPosition = -1;
    Fragment mNewAppointmentFragment;
    Fragment mBrowseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

//        BrowseFragment browseFragment = BrowseFragment.newInstance();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.container_browser, browseFragment);
//        transaction.commit();
        changeFragment(1);

    }

    public void changeFragment(int position){
        if (position == mCurrentPosition){
            return;
        }
        Fragment fragment = getFragment(position);
        if (fragment == null){
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        if (fragment.isAdded()) {
            if (fragment.isHidden()) {
                transaction.show(fragment)
                        .hide(getFragment(mCurrentPosition));
            }
        } else {
            if (mCurrentPosition != -1) {
                transaction.add(R.id.container_browser, fragment)
                        .hide(getFragment(mCurrentPosition));
            } else {
                transaction.add(R.id.container_browser, fragment);
            }
        }
        transaction.commit();
        mCurrentPosition = position;
    }

    public Fragment getFragment(int position) {
        switch (position) {
            case 1:
                if (mBrowseFragment == null) {
                    mBrowseFragment = BrowseFragment.newInstance();
                }
                return mBrowseFragment;
            case 2:
                if (mNewAppointmentFragment == null){
                    mNewAppointmentFragment = NewAppointmentFragment.newInstance();
                }
                return mNewAppointmentFragment;
            default:
                return null;
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mCurrentPosition == 2){
            changeFragment(1);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}
