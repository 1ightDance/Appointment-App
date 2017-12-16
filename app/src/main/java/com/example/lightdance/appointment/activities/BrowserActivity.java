package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.fragments.BrowseFragment;
import com.example.lightdance.appointment.fragments.NewAppointmentFragment;
import com.example.lightdance.appointment.fragments.TimePickerFragment;

/**
 * @author pope
 */
public class BrowserActivity extends AppCompatActivity implements TimePickerFragment.timeListener {

    private int mCurrentPosition = -1;
    private int typeCode = 0;
    Fragment mNewAppointmentFragment;
    Fragment mBrowseFragment;

    /**
     * from == 1 通过点击TypeFragment的ItemView开启本Activity，意图加载活动广场碎片
     * from == 2 通过点击TypeFragment的FloatingBar开启本Activity，意图加载发布新活动碎片
     * from == 3 通过点击AppointmentDetailed的编辑按钮开启本Activity，意图加载发布新活动碎片
     */
    private int from;
    private String editObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Intent intent = getIntent();
        from = intent.getIntExtra("from", 1);
        typeCode = intent.getIntExtra("typeCode", 0);
        editObjectId = intent.getStringExtra("editObjectId");
        if (from == 1) {
            if (typeCode == 0) {
                Toast.makeText(this, "选择的活动类型的数据传输出现错误", Toast.LENGTH_LONG).show();
            } else {
                BrowseFragment browseFragment = (BrowseFragment) getFragment(1);
                browseFragment.sendSelectType(typeCode);
                changeFragment(1);
            }
        }
        if (from == 2||from == 3){
            changeFragment(2);
        }
    }

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
                if (mNewAppointmentFragment == null) {
                    mNewAppointmentFragment = NewAppointmentFragment.newInstance();
                }
                return mNewAppointmentFragment;
            default:
                return null;
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurrentPosition == 2) {
            if (from == 1) {
                BrowseFragment browseFragment = (BrowseFragment) getFragment(1);
                browseFragment.initBrowserData();
                changeFragment(1);
            }
            if (from == 2){
                finish();
            }
            if (from == 3){
                Intent intent = new Intent(this, AppointmentDetailActivity.class);
                intent.putExtra("objectId",editObjectId);
                startActivity(intent);
                finish();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 重写TimeListener接口的saveSelectTime方法
     * 传入需要被保存的年、月、日、时、分数据
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 时
     * @param minute 分
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void saveSelectTime(int year, int month, int day, int hour, int minute) {
        NewAppointmentFragment newAppointmentFragment = (NewAppointmentFragment) getFragment(2);
        newAppointmentFragment.ensureTimeRight(year, month, day, hour, minute);
    }

    /**
     * @return 返回FromCode 即返回该Activity从哪里启动来的
     */
    public int getFromCode(){
        return from;
    }

    public String getEditObjectId(){
        return editObjectId;
    }

}
