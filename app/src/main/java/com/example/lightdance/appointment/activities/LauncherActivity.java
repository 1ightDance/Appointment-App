package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

/**
 * @author pope
 */
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //设置等待时间，单位为毫秒
        Integer time = 500;
        Handler handler = new Handler();

        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(LauncherActivity.this, MainActivity.class));
                LauncherActivity.this.finish();
            }
        }, time);
    }
}
