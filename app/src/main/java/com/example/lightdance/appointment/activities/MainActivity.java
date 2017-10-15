package com.example.lightdance.appointment.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.fragments.BrowseFragment;
import com.example.lightdance.appointment.fragments.NewAppointmentFragment;
import com.example.lightdance.appointment.fragments.NewsFragment;
import com.example.lightdance.appointment.fragments.PersonalCenterFragment;
import com.example.lightdance.appointment.fragments.TimePickerFragment;

public class MainActivity extends AppCompatActivity implements TimePickerFragment.timeListener {

    private int yearSelect;
    private int monthSelect;
    private int daySelect;
    private int hourSelect;
    private int minuteSelect;
    private int mCurrentPosition = -1;

    private BottomNavigationView bottomNavigationView;
    Fragment mNewAppointmentFragment;
    Fragment mBrowesrFragmrnt;
    Fragment mPersonalCenterFragmrnt;
    Fragment mNewsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //TODO 重写changeFragment方法 目前存在BUG
        changeFragment(1);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.main_bottomnavigationview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_browse:
                        changeFragment(1);
                        break;
                    case R.id.menu_news:
                        changeFragment(2);
                        break;
                    case R.id.menu_appointment:
                        changeFragment(3);
                        break;
                    case R.id.menu_me:
                        changeFragment(4);
                        break;
                }
                return true;
            }

        });
    }

    //动态加载碎片的方法 TEST
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
                transaction.add(R.id.container, fragment)
                        .hide(getFragment(mCurrentPosition));
            } else {
                transaction.add(R.id.container, fragment);
            }
        }
        transaction.commit();
        mCurrentPosition = position;
    }

    private Fragment getFragment(int position) {
        switch (position) {
            case 0:
                if (mBrowesrFragmrnt == null) {
                    mBrowesrFragmrnt = BrowseFragment.newInstance();
                }
                return mBrowesrFragmrnt;
            case 1:
                if (mNewsFragment == null) {
                    mNewsFragment = NewsFragment.newInstance();
                }
                return mNewsFragment;
            case 2:
                if (mNewAppointmentFragment == null) {
                    mNewAppointmentFragment = NewAppointmentFragment.newInstance();
                }
                return mNewAppointmentFragment;
            case 3:
                if (mPersonalCenterFragmrnt == null) {
                    mPersonalCenterFragmrnt = PersonalCenterFragment.newInstance();
                }
                return mPersonalCenterFragmrnt;
            default:
                return null;
        }
    }

    //用以更改日期的方法
    public void changeData(){
        NewAppointmentFragment newAppointmentFragment = NewAppointmentFragment.newInstance();
        newAppointmentFragment.setDate(yearSelect,monthSelect,daySelect);
    }

    //用以更改时间的方法
    private void changeTime() {
        NewAppointmentFragment newAppointmentFragment = NewAppointmentFragment.newInstance();
        newAppointmentFragment.setTime(hourSelect,minuteSelect);
    }

    //重写TimeListener接口的saveDate方法
    @Override
    public void saveDate(int year, int month, int day) {
        yearSelect  = year;
        monthSelect = month;
        daySelect   = day;
        changeData();
    }

    //重写TimeListener接口的saveTime方法
    @Override
    public void saveTime(int hour, int minute) {
        hourSelect   = hour;
        minuteSelect = minute;
        changeTime();
    }
}