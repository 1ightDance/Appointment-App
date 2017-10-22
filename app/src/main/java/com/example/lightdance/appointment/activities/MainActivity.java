package com.example.lightdance.appointment.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.lightdance.appointment.Model.BrowseMsgBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.fragments.BrowseFragment;
import com.example.lightdance.appointment.fragments.MessageFragment;
import com.example.lightdance.appointment.fragments.NewAppointmentFragment;
import com.example.lightdance.appointment.fragments.NewsFragment;
import com.example.lightdance.appointment.fragments.PersonalCenterFragment;
import com.example.lightdance.appointment.fragments.TimePickerFragment;
import com.example.lightdance.appointment.fragments.WarningFragment;

public class MainActivity extends AppCompatActivity implements TimePickerFragment.timeListener {

    private int yearSelect;
    private int monthSelect;
    private int daySelect;
    private int hourSelect;
    private int minuteSelect;
    private int mCurrentPosition = -1;

    private boolean added = false;

    private BottomNavigationView bottomNavigationView;
    Fragment mNewAppointmentFragment;
    Fragment mBrowesrFragment;
    Fragment mPersonalCenterFragment;
    Fragment mNewsFragment;
    Fragment mMessageFragment;
    Fragment mWarningFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加载预览数据
        previewDataLoading();

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

        changeFragment(2);
        changeNavigationSelected(R.id.menu_news);

    }

    //判断是否加载过预览数据
    private void previewDataLoading() {
        SharedPreferences sharedPreferences = getSharedPreferences("dataLoaded",MODE_PRIVATE);
        added = sharedPreferences.getBoolean("isLoaded",false);
        if (added == false) {
            initAppointmentMsg();
        }
    }

    //加载广场预览数据方法
    private void initAppointmentMsg() {
        BrowseMsgBean user1 = new BrowseMsgBean();
        user1.setTitle("双休日图书馆6楼讨论室自习");
        user1.setTypeIconId(R.drawable.ic_study);
        user1.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user1.setInviterIconId(R.mipmap.headshot_1);
        user1.setInviter("花落的速度");
        user1.setPlace("双休日图书馆6楼");
        user1.setPersonNumberHave("1");
        user1.setPersonNumberNeed("4");
        user1.save();
        BrowseMsgBean user2 = new BrowseMsgBean();
        user2.setTitle("周六晚上五餐三楼狼人杀");
        user2.setTypeIconId(R.drawable.ic_brpg);
        user2.setContent("狼人杀杀起来啊！牌我自备！人均至少消费的那一杯算我头上，你们只管来！先到先得 12人局 顺便交波朋友");
        user2.setInviterIconId(R.mipmap.headshot_2);
        user2.setInviter("教皇");
        user2.setPlace("五餐三楼");
        user2.setPersonNumberHave("3");
        user2.setPersonNumberNeed("12");
        user2.save();
        BrowseMsgBean user3 = new BrowseMsgBean();
        user3.setTitle("20号晚上弗雷德密室逃脱");
        user3.setTypeIconId(R.drawable.ic_game);
        user3.setContent("喜欢紧张刺激的密室逃脱 但是身边朋友都没兴趣 想来约一波兴趣相投的朋友一起去（门票费自理）");
        user3.setInviterIconId(R.mipmap.headshot_3);
        user3.setInviter("caozh");
        user3.setPlace("杭电生活区东门集合一起出发");
        user3.setPersonNumberHave("6");
        user3.setPersonNumberNeed("10");
        user3.save();
        BrowseMsgBean user4 = new BrowseMsgBean();
        user4.setTitle("云南自由行");
        user4.setTypeIconId(R.drawable.ic_travel);
        user4.setContent("有计划去云南旅游的或者想出去旅游但不知道去哪里的朋友嘛？运动会期间会有至少四天假期，可以出去玩一玩~跟我一起说走就走吧！费用自行承担，装备自行准备");
        user4.setInviterIconId(R.mipmap.headshot_4);
        user4.setInviter("大毛");
        user4.setPlace("云南昆明");
        user4.setPersonNumberHave("1");
        user4.setPersonNumberNeed("8");
        user4.save();
        BrowseMsgBean user5 = new BrowseMsgBean();
        user5.setTitle("世纪难题——减肥");
        user5.setTypeIconId(R.drawable.ic_sports);
        user5.setContent("求一起减肥的胖友跑步，每周五六日晚跑一个小时，恶劣天气除外 有兴趣的戳进来加我Q。。。");
        user5.setInviterIconId(R.mipmap.headshot_5);
        user5.setInviter("蛋白质");
        user5.setPlace("杭电东操场");
        user5.setPersonNumberHave("1");
        user5.setPersonNumberNeed("2");
        user5.save();
        BrowseMsgBean user6 = new BrowseMsgBean();
        user6.setTitle("约饭");
        user6.setTypeIconId(R.drawable.ic_dining);
        user6.setContent("本人有一张高沙胖哥俩满500可享5折的优惠券，自己吃不到500，独乐乐不如众乐乐顺便交朋友，费用AA");
        user6.setInviterIconId(R.mipmap.headshot_6);
        user6.setInviter("吉瑞斯的指环");
        user6.setPlace("1餐门口17：30集合一起过去");
        user6.setPersonNumberHave("1");
        user6.setPersonNumberNeed("8");
        user6.save();
        BrowseMsgBean user7 = new BrowseMsgBean();
        user7.setTitle("寻参加完宁波造音节返校同路小伙伴");
        user7.setTypeIconId(R.drawable.ic_others);
        user7.setContent("找造音节结束接着赶回学校的嘻哈爱好者同路，到杭州后也比较晚了，可以拼车回学校，费用AA");
        user7.setInviterIconId(R.mipmap.headshot_7);
        user7.setInviter("契鸽");
        user7.setPlace("宁波火车站");
        user7.setPersonNumberHave("1");
        user7.setPersonNumberNeed("4");
        user7.save();
        BrowseMsgBean user8 = new BrowseMsgBean();
        user8.setTitle("约22号电影《羞羞的铁拳》");
        user8.setTypeIconId(R.drawable.ic_movies);
        user8.setContent("本来和室友一起买了22号《羞羞的铁拳》，但是室友临时有事，转让电影票 价格可小刀");
        user8.setInviterIconId(R.mipmap.headshot_8);
        user8.setInviter("ZhengHQ");
        user8.setPlace("中影星城都尚影院");
        user8.setPersonNumberHave("1");
        user8.setPersonNumberNeed("2");
        user8.save();
        SharedPreferences.Editor editor = getSharedPreferences("dataLoaded",MODE_PRIVATE).edit();
        editor.putBoolean("isLoaded",true);
        editor.apply();
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

        //判断是否登录 是否显示广场碎片和消息碎片
        if (position == 1){
            SharedPreferences preferences = getSharedPreferences("loginData"
                    , Context.MODE_PRIVATE);
            boolean isLogined = preferences.getBoolean("isLogined", false);
            if (isLogined == false) {
                changeFragment(6);
                return;
            }
        }
        if (position == 3){
            SharedPreferences preferences = getSharedPreferences("loginData"
                    , Context.MODE_PRIVATE);
            boolean isLogined = preferences.getBoolean("isLogined", false);
            if (isLogined == false) {
                changeFragment(6);
                return;
            }
        }

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

    //获取碎片实例方法
    public Fragment getFragment(int position) {
        switch (position) {
            case 1:
                if (mBrowesrFragment == null) {
                    mBrowesrFragment = BrowseFragment.newInstance();
                }
                return mBrowesrFragment;
            case 2:
                if (mNewsFragment == null) {
                    mNewsFragment = NewsFragment.newInstance();
                }
                return mNewsFragment;
            case 3:
                if (mMessageFragment == null) {
                    mMessageFragment = MessageFragment.newInstance();
                }
                return mMessageFragment;
            case 4:
                if (mPersonalCenterFragment == null) {
                    mPersonalCenterFragment = PersonalCenterFragment.newInstance();
                }
                return mPersonalCenterFragment;
            case 5:
                if (mNewAppointmentFragment == null){
                    mNewAppointmentFragment = NewAppointmentFragment.newInstance();
                }
                return mNewAppointmentFragment;
            case 6:
                if (mWarningFragment == null){
                    mWarningFragment = WarningFragment.newInstance();
                }
                return mWarningFragment;

            default:
                return null;
        }
    }

    //用以更改日期的方法
    public void changeData(){
        NewAppointmentFragment newAppointmentFragment = (NewAppointmentFragment) getFragment(5);
        newAppointmentFragment.setDate(yearSelect,monthSelect,daySelect);
    }

    //用以更改时间的方法
    private void changeTime() {
        NewAppointmentFragment newAppointmentFragment = (NewAppointmentFragment) getFragment(5);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 改变NavigationBar被选中Item的方法
    public void changeNavigationSelected(int selectedId) {
        bottomNavigationView.setSelectedItemId(selectedId);
    }
}