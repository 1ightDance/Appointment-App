package com.example.lightdance.appointment.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.Model.JoinedHistoryBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.BrowserMsgAdapter;
import com.example.lightdance.appointment.fragments.JoinedAppointmentFragment;
import com.example.lightdance.appointment.fragments.MyAppointmentFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

public class AppointmentHistoryActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * 声明两个Fragment类的对象，分别用于存放自定义的两个子类。
     * 具体见{@link MyAppointmentFragment}和{@link JoinedAppointmentFragment}
     * 在getItem中用switch-case语句返回相应fragment并显示
     */
    private MyAppointmentFragment myAppointmentFragment;
    private JoinedAppointmentFragment joinedAppointmentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.history_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.history_tabs);
        //tabLayout.setupWithViewPager(mViewPager);//感觉这一行与下面两行功能重复，但是不知该如何替换

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<View> mViewList;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * getItem is called to instantiate the fragment for the given page.
         * Return a PlaceholderFragment (defined as a static inner class below).
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (myAppointmentFragment == null){
                        myAppointmentFragment = new MyAppointmentFragment();
                    }
                    return myAppointmentFragment;
                case 1:
                    if (joinedAppointmentFragment == null){
                        joinedAppointmentFragment = new JoinedAppointmentFragment();
                    }
                    return joinedAppointmentFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "发布";
                case 1:
                    return "应约";
                default:
            }
            return null;
        }
    }
}
