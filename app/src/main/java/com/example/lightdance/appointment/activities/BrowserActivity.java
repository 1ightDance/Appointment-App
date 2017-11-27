package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.os.Bundle;
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
     * from == 1 加载活动广场碎片
     * from == 2 加载发布新活动碎片
     */
    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Intent intent = getIntent();
        from = intent.getIntExtra("from", 1);
        typeCode = intent.getIntExtra("typeCode", 0);
        if (from == 1) {
            if (typeCode == 0) {
                Toast.makeText(this, "选择的活动类型的数据传输出现错误", Toast.LENGTH_LONG).show();
            } else {
                BrowseFragment browseFragment = (BrowseFragment) getFragment(1);
                browseFragment.sendSelectType(typeCode);
                changeFragment(1);
            }
        }
        if (from == 2){
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
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mCurrentPosition == 2) {
            if (from == 1) {
                BrowseFragment browseFragment = (BrowseFragment) getFragment(1);
                browseFragment.initBrowserData();
                changeFragment(1);
            }
            if (from == 2){
                finish();
            }
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    //重写TimeListener接口的saveSelectTime方法
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

}
