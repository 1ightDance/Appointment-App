package com.example.lightdance.appointment.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.JoinedHistoryBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.fragments.MessageFragment;
import com.example.lightdance.appointment.fragments.NewsFragment;
import com.example.lightdance.appointment.fragments.PersonalCenterFragment;
import com.example.lightdance.appointment.fragments.TypeFragment;
import com.example.lightdance.appointment.fragments.WarningFragment;

import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author lightdance
 */
public class MainActivity extends AppCompatActivity {

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
        BmobSMS.initialize(this, "7420a33e6758604ec1e823f1378f4e61");

        //检查是否有未完成反馈的已结束活动
        checkIsComment();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottomnavigationview);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
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
                    default:
                        break;
                }
                return true;
            }

        });

        changeFragment(1);

    }

    private void checkIsComment() {
        //检测是否登录 若登录则检查是否有未反馈活动
        SharedPreferences preferences = getSharedPreferences("loginData"
                , Context.MODE_PRIVATE);
        boolean isLogined = preferences.getBoolean("isLogined", false);
        if (isLogined) {
            String userObjectId = preferences.getString("userBeanId", "出错");
            BmobQuery<JoinedHistoryBean> query = new BmobQuery<>();
            query.addWhereEqualTo("userObjectId", userObjectId);
            query.findObjects(new FindListener<JoinedHistoryBean>() {
                @Override
                public void done(List<JoinedHistoryBean> list, BmobException e) {
                    if (e == null) {
                        JoinedHistoryBean joinedHistoryBean = list.get(0);
                        List<String> list1 = joinedHistoryBean.getNoComment();
                        if (list1 == null) {
                            // 说明用户在该表中还没有数据
                        } else {
                            if (list1.size() != 0) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                dialog.setTitle("通知");
                                dialog.setMessage("您还有已经结束的活动未作出反馈哟\n您的反馈对于其他用户来说都是一个非常重要的参考依据，希望得到您的配合！");
                                dialog.setPositiveButton("去反馈", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MainActivity.this, CommentActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                dialog.setNegativeButton("再等等", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            } else {
                                // 说明用户在该表中的未评论活动中数据为null
                            }
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "e错误" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    /**
     * 动态加载碎片的方法
     *
     * @param position 需要跳转到的碎片位置
     */
    public void changeFragment(int position) {
        if (position == mCurrentPosition) {
            return;
        }
        Fragment fragment = getFragment(position);
        if (fragment == null) {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        //如果即将跳转3或5号碎片 判断该用户是否登录
        if (position == 3 || position == 5) {
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

    /**
     * 获取碎片实例方法
     *
     * @param position 传入获取碎片编号
     * @return 返回编号对应的碎片
     */
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
                if (mWarningFragment == null) {
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

    /**
     * 改变NavigationBar被选中Item的方法
     *
     * @param selectedId 被选中的Item的Id
     */
    public void changeNavigationSelected(int selectedId) {
        bottomNavigationView.setSelectedItemId(selectedId);
    }

    /**
     * 若在NewAppointment碎片则back键实现返回效果
     * 若在其他碎片 则back键实现双击退出应用程序
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
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