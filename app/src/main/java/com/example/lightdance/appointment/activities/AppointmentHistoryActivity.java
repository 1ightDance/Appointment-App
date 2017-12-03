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
    private Fragment myAppointmentFragment;
    private Fragment joinedAppointmentFragment;

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
        tabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        /**
         *用来取数据库数据
         */
        private BmobQuery<BrowserMsgBean> msgHistoryList;
        private BmobQuery<JoinedHistoryBean> joinedHistoryList;
        RecyclerView historyRecyclerView;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            //绑定视图
            final View rootView = inflater.inflate(R.layout.fragment_my_appointment_history, container, false);
            //有点害怕这里设置完查询记录为空的textview之后不会消失
            historyRecyclerView = (RecyclerView)rootView.findViewById(R.id.section_recyclerview);
            historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * getItem is called to instantiate the fragment for the given page.
         * Return a PlaceholderFragment (defined as a static inner class below).
         */
        @Override
        public Fragment getItem(int position) {

            /*TODO 不确定是不是得写在这里*/
            switch (position){
                case 1:
                    return new MyAppointmentFragment();
                case 2:
                    return new JoinedAppointmentFragment();
                default:
                    return null;
            }

            //return PlaceholderFragment.newInstance(position + 1);//害怕有用就没敢删
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
