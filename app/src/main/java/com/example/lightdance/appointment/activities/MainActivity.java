package com.example.lightdance.appointment.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.fragments.MessageFragment;
import com.example.lightdance.appointment.fragments.NewsFragment;
import com.example.lightdance.appointment.fragments.PersonalCenterFragment;
import com.example.lightdance.appointment.fragments.TypeFragment;
import com.example.lightdance.appointment.fragments.WarningFragment;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity{

    private int mCurrentPosition = -1;
    private long firstTime = 0;

    private boolean squareDataAdded = false;
    private boolean userMsgAdded = false;

    private BottomNavigationView bottomNavigationView;
    Fragment mPersonalCenterFragment;
    Fragment mNewsFragment;
    Fragment mMessageFragment;
    Fragment mWarningFragment;
    Fragment mTypeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化Bmob SDK
        Bmob.initialize(this, "7420a33e6758604ec1e823f1378f4e61");

        //加载预览数据
//        previewDataLoading();

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

        changeFragment(1);

    }

    //判断是否加载过预览数据
    private void previewDataLoading() {
        SharedPreferences sharedPreferences = getSharedPreferences("dataLoaded",MODE_PRIVATE);
        squareDataAdded = sharedPreferences.getBoolean("squareDataLoaded",false);
        userMsgAdded = sharedPreferences.getBoolean("userMsgLoaded",false);
        if (squareDataAdded == false) {
            initAppointmentMsg();
        }
        if (userMsgAdded == false){
            initUserMsg();
        }
    }

    private void initUserMsg() {
        UserBean user1 = new UserBean();
        user1.setUserNickName("花落的速度");
        user1.setUserDescription("一个人知道自己为什么而活，就可以忍受任何一种生活。");
        user1.setUserIconId(R.mipmap.headshot_1);
//        user1.save();
        UserBean user2 = new UserBean();
        user2.setUserNickName("教皇");
        user2.setUserDescription("人有时只需静静地看，悲伤也成享受。");
        user2.setUserIconId(R.mipmap.headshot_2);
//        user2.save();
        UserBean user3 = new UserBean();
        user3.setUserNickName("caozh");
        user3.setUserDescription("人性本凉薄，又何介，谁比谁更多。   \n" +
                "心，若没有栖息的地方，到哪里都是流浪……");
        user3.setUserIconId(R.mipmap.headshot_3);
//        user3.save();
        UserBean user4 = new UserBean();
        user4.setUserNickName("大毛");
        user4.setUserDescription("诗人用云雾塑造形象，他也是废墟中捏弄残灰的王。");
        user4.setUserIconId(R.mipmap.headshot_4);
//        user4.save();
        UserBean user5 = new UserBean();
        user5.setUserNickName("蛋白质");
        user5.setUserDescription("年轻时，你做了一个决定，要把自己的生命献给爱情。 后来，你没死，年轻替你抵了命。");
        user5.setUserIconId(R.mipmap.headshot_5);
//        user5.save();
        UserBean user6 = new UserBean();
        user6.setUserNickName("吉瑞斯的指环");
        user6.setUserDescription("深情若是一桩悲剧 必定以死来句读");
        user6.setUserIconId(R.mipmap.headshot_6);
//        user6.save();
        UserBean user7 = new UserBean();
        user7.setUserNickName("契鸽");
        user7.setUserDescription("每一个不曾起舞的日子，都是对生命的辜负。");
        user7.setUserIconId(R.mipmap.headshot_7);
//        user7.save();
        UserBean user8 = new UserBean();
        user8.setUserNickName("ZhengHQ");
        user8.setUserDescription("我是你路上最后的一个过客，最后的一个春天，最后的一场雪，最后的一次求生的战争。——保尔·艾吕雅");
        user8.setUserIconId(R.mipmap.headshot_8);
//        user8.save();
        SharedPreferences.Editor editor = getSharedPreferences("dataLoaded",MODE_PRIVATE).edit();
        editor.putBoolean("userMsgLoaded",true);
        editor.apply();
    }

    //加载广场预览数据方法
    private void initAppointmentMsg() {
        BrowserMsgBean user1 = new BrowserMsgBean();
        user1.setTitle("图书馆6楼自习");
        user1.setTypeIconId(R.drawable.ic_study);
        user1.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user1.setInviterIconId(R.mipmap.headshot_1);
        user1.setInviter("花落的速度");
        user1.setPlace("双休日图书馆6楼");
        user1.setStartTime("2017/10/14 14:00");
        user1.setEndTime("2017/10/18 19:30");
        user1.setContactWay("可以加我的qq1173038073 验证信息填“约吧”");
        user1.setPersonNumberHave(1);
        user1.setPersonNumberNeed("4");
        user1.setTypeCode(1);
        user1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
        BrowserMsgBean user2 = new BrowserMsgBean();
        user2.setTitle("这周周六晚上五餐三楼狼人杀！！！");
        user2.setTypeIconId(R.drawable.ic_brpg);
        user2.setContent("狼人杀杀起来啊！牌我自备！人均至少消费的那一杯算我头上，你们只管来！先到先得 12人局 顺便交波朋友");
        user2.setInviterIconId(R.mipmap.headshot_2);
        user2.setInviter("教皇");
        user2.setPlace("五餐三楼");
        user2.setStartTime("2017/10/14 14:00");
        user2.setEndTime("2017/10/18 19:30");
        user2.setContactWay("可以加我的qq1173038073 验证信息填“约吧”");
        user2.setPersonNumberHave(3);
        user2.setPersonNumberNeed("12");
        user2.setTypeCode(3);
        user2.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
        BrowserMsgBean user3 = new BrowserMsgBean();
        user3.setTitle("弗雷德密室逃脱");
        user3.setTypeIconId(R.drawable.ic_game);
        user3.setContent("喜欢紧张刺激的密室逃脱 但是身边朋友都没兴趣 想来约一波兴趣相投的朋友一起去（门票费自理）");
        user3.setInviterIconId(R.mipmap.headshot_3);
        user3.setInviter("caozh");
        user3.setPlace("杭电生活区东门集合一起出发");
        user3.setStartTime("2017/10/14 14:00");
        user3.setEndTime("2017/10/18 19:30");
        user3.setContactWay("可以加我的qq1173038073 验证信息填“约吧”");
        user3.setPersonNumberHave(6);
        user3.setPersonNumberNeed("10");
        user3.setTypeCode(4);
        user3.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }});
        BrowserMsgBean user4 = new BrowserMsgBean();
        user4.setTitle("云南自由行");
        user4.setTypeIconId(R.drawable.ic_travel);
        user4.setContent("有计划去云南旅游的或者想出去旅游但不知道去哪里的朋友嘛？运动会期间会有至少四天假期，可以出去玩一玩~跟我一起说走就走吧！费用自行承担，装备自行准备");
        user4.setInviterIconId(R.mipmap.headshot_4);
        user4.setStartTime("2017/10/14 14:00");
        user4.setEndTime("2017/10/18 19:30");
        user4.setContactWay("可以加我的qq1173038073 验证信息填“约吧”");
        user4.setInviter("大毛");
        user4.setPlace("云南昆明");
        user4.setPersonNumberHave(1);
        user4.setPersonNumberNeed("8");
        user4.setTypeCode(8);
        user4.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
        BrowserMsgBean user5 = new BrowserMsgBean();
        user5.setTitle("世纪难题——减肥");
        user5.setTypeIconId(R.drawable.ic_sports);
        user5.setContent("求一起减肥的胖友跑步，每周五六日晚跑一个小时，恶劣天气除外 有兴趣的戳进来加我Q。。。");
        user5.setInviterIconId(R.mipmap.headshot_5);
        user5.setInviter("蛋白质");
        user5.setPlace("杭电东操场");
        user5.setStartTime("2017/10/14 14:00");
        user5.setEndTime("2017/10/18 19:30");
        user5.setContactWay("可以加我的qq1173038073 验证信息填“约吧”");
        user5.setPersonNumberHave(1);
        user5.setPersonNumberNeed("2");
        user5.setTypeCode(6);
        user5.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
        BrowserMsgBean user6 = new BrowserMsgBean();
        user6.setTitle("约饭");
        user6.setTypeIconId(R.drawable.ic_dining);
        user6.setContent("本人有一张高沙胖哥俩满500可享5折的优惠券，自己吃不到500，独乐乐不如众乐乐顺便交朋友，费用AA");
        user6.setInviterIconId(R.mipmap.headshot_6);
        user6.setInviter("吉瑞斯的指环");
        user6.setPlace("1餐门口17：30集合一起过去");
        user6.setStartTime("2017/10/14 14:00");
        user6.setEndTime("2017/10/18 19:30");
        user6.setContactWay("可以加我的qq1173038073 验证信息填“约吧”");
        user6.setPersonNumberHave(1);
        user6.setPersonNumberNeed("8");
        user6.setTypeCode(7);
        user6.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
        BrowserMsgBean user7 = new BrowserMsgBean();
        user7.setTitle("寻参加完宁波造音节返校同路小伙伴");
        user7.setTypeIconId(R.drawable.ic_others);
        user7.setContent("找造音节结束接着赶回学校的嘻哈爱好者同路，到杭州后也比较晚了，可以拼车回学校，费用AA");
        user7.setInviterIconId(R.mipmap.headshot_7);
        user7.setInviter("契鸽");
        user7.setPlace("宁波火车站");
        user7.setStartTime("2017/10/14 14:00");
        user7.setEndTime("2017/10/18 19:30");
        user7.setContactWay("可以加我的qq1173038073 验证信息填“约吧”");
        user7.setPersonNumberHave(1);
        user7.setPersonNumberNeed("4");
        user7.setTypeCode(9);
        user7.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
        BrowserMsgBean user8 = new BrowserMsgBean();
        user8.setTitle("约22号电影《羞羞的铁拳》");
        user8.setTypeIconId(R.drawable.ic_movies);
        user8.setContent("本来和室友一起买了22号《羞羞的铁拳》，但是室友临时有事，转让电影票 价格可小刀");
        user8.setInviterIconId(R.mipmap.headshot_8);
        user8.setInviter("ZhengHQ");
        user8.setPlace("中影星城都尚影院");
        user8.setStartTime("2017/10/14 14:00");
        user8.setEndTime("2017/10/18 19:30");
        user8.setContactWay("可以加我的qq1173038073 验证信息填“约吧”");
        user8.setPersonNumberHave(1);
        user8.setPersonNumberNeed("2");
        user8.setTypeCode(2);
        user8.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

            }
        });
        SharedPreferences.Editor editor = getSharedPreferences("dataLoaded",MODE_PRIVATE).edit();
        editor.putBoolean("squareDataLoaded",true);
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
                if (mTypeFragment == null) {
                    mTypeFragment = TypeFragment.newInstance();
                }
                return mTypeFragment;
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
            case 6:
                if (mWarningFragment == null){
                    mWarningFragment = WarningFragment.newInstance();
                }
                return mWarningFragment;
            default:
                return null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 改变NavigationBar被选中Item的方法
    public void changeNavigationSelected(int selectedId) {
        bottomNavigationView.setSelectedItemId(selectedId);
    }

    /* 若在NewAppointment碎片则back键实现返回效果
    *  若在其他碎片 则back键实现双击退出应用程序
    * */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mCurrentPosition == 5){
            changeFragment(1);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = secondTime;
                return true;
            } else {
                System.exit(0);
            }
        }
        return super.onKeyUp(keyCode, event);
    }
}