package com.example.lightdance.appointment.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    private BottomNavigationView bottomNavigationView;

    private NewAppointmentFragment newAppointmentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newAppointmentFragment = new NewAppointmentFragment();

        //TODO 重写changeFragment方法 目前存在BUG
        //已将广场放在最前，上面的newAppointmentFargment感觉可以去掉，
        //不能删 后面还用到呢（黄旗）
        //然后咋把NewAppointmentFragment的方法@changeDate() @changeTime()放在这里了？（天舒）
        //因为数据是从DialogFragment碎片传到MainActivity再传到NewAppointment的 所以这里有这两个方法（黄旗）
        BrowseFragment browseFragment = new BrowseFragment();
        changeFragment(browseFragment);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.main_bottomnavigationview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_appointment:
                        changeFragment(new NewAppointmentFragment());
                        break;
                    case R.id.menu_browse:
                        changeFragment(new BrowseFragment());
                        break;
                    case R.id.menu_news:
                        changeFragment(new NewsFragment());
                        break;
                    case R.id.menu_me:
                        changeFragment(new PersonalCenterFragment());
                        break;
                }
                return true;
            }

        });
    }

    //动态加载碎片的方法 TEST
    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }

    //用以更改日期的方法
    public void changeData(){
        newAppointmentFragment.setDate(yearSelect,monthSelect,daySelect);
    }

    //用以更改时间的方法
    private void changeTime() {
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