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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.Model.JoinedHistoryBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.BrowserMsgAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
     * 声明两个fragment，并在初始化时获取数据库信息并绑定相应adapter，
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
            final View rootView = inflater.inflate(R.layout.fragment_appointment_history, container, false);
            //有点害怕这里设置完查询记录为空的textview之后不会消失
            final TextView ifEmpty = (TextView) rootView.findViewById(R.id.history_if_empty);
            historyRecyclerView = (RecyclerView)rootView.findViewById(R.id.section_recyclerview);
            historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



            SharedPreferences preferences = getActivity().getSharedPreferences("loginData",MODE_PRIVATE);
            String loginStudentId = preferences.getString("userBeanId",null);
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                //TODO 暂时这样,希望之后能有个每次加载10行，下拉加载更多的逻辑,目前设置的只查50条是有隐患的
                msgHistoryList = new BmobQuery<>();
                msgHistoryList.addWhereEqualTo("inviter" , loginStudentId);
                msgHistoryList.setLimit(50);
                msgHistoryList.findObjects(new FindListener<BrowserMsgBean>() {
                    @Override
                    public void done(List<BrowserMsgBean> list, BmobException e) {
                        if (e == null) {
                            //查询成功，读取数据以供adapter保存
                            //Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();//debug
                            BrowserMsgAdapter adapter = new BrowserMsgAdapter(getActivity(), list);
                            historyRecyclerView.setAdapter(adapter);
                            //如果为空，显示这样的字段
                            if (list.isEmpty()) {
                                ifEmpty.setText("发布记录空空如也");
                            }
                        } else {
                            //TODO 这里在考虑加幅网络连接失败图片啥的，但是不太会搞定那个抛异常
                            Toast.makeText(getActivity(), "你网有毛病吧"
                                    +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                msgHistoryList = DataSupport.where("inviter == ?",loginNickName).find(BrowseMsgBean.class);
//                BrowserMsgAdapter adapter = new BrowserMsgAdapter(getActivity(),msgHistoryList);
//                this.historyRecyclerView.setAdapter(adapter);
                }else if (getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                /**
                 * * 查询所有约帖中“应约者列表”，显示所有含有本账号的约帖 ...
                 * TODO {@methord setLimit}存在同上隐患
                 */
                joinedHistoryList = new BmobQuery<>();

                /*先查询应约过的帖子Id，并存个数组*/
                String basicSql = "select browserIdList from JoinedHistoryBean where userObjectId = " + loginStudentId + ";";
                joinedHistoryList.doSQLQuery(basicSql, new SQLQueryListener<JoinedHistoryBean>() {
                    @Override
                    public void done(BmobQueryResult<JoinedHistoryBean> bmobQueryResult, BmobException e) {
                        if(e ==null){
                            List<JoinedHistoryBean> list = bmobQueryResult.getResults();
                            List<String> msgIds = new ArrayList<>();
                            while(list.iterator().hasNext()){
                                msgIds.add(list.iterator().next().getUserObjectId());
                            }
                            if(list!=null && list.size()>0){
                                /*查询结果不为空，通过查到的帖子id列表，向发帖列表中查询对应帖子，并通过适配器显示*/
                                msgHistoryList = new BmobQuery<>();
                                msgHistoryList.addWhereContainedIn("objectId" , Arrays.asList(msgIds));
                                msgHistoryList.findObjects(new FindListener<BrowserMsgBean>() {
                                    @Override
                                    public void done(List<BrowserMsgBean> list, BmobException e) {
                                        if(e==null){
                                            //debug
                                            Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
                                            BrowserMsgAdapter adapter = new BrowserMsgAdapter(getActivity(), list);
                                            historyRecyclerView.setAdapter(adapter);
                                        }else{
                                            Toast.makeText(getActivity() , "查询失败："+e.toString() , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                /*应约列表为空，设置TextView提示*/
                                ifEmpty.setText("应约记录空空如也");
                            }
                        }else{
                            Log.i("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                        }
                    }
                });
            }
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

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            /*TODO 不确定是不是得写在这里*/
            switch (position){
            }

            return PlaceholderFragment.newInstance(position + 1);
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
