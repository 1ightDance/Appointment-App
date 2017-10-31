package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LogoutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_logout)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        ButterKnife.bind(this);

        toolbar.setTitle("账号管理");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogoutActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    @OnClick(R.id.btn_logout)
    public void onViewClicked() {
        SharedPreferences.Editor editor = getSharedPreferences("loginData",MODE_PRIVATE).edit();
        editor.putBoolean("isLogined",false);
        editor.putString("nickName","空");
        editor.putInt("userAvatar",0);
        editor.putString("userIntroduction","空");
        editor.putString("userName","空");
        editor.putString("userSex","空");
        editor.putString("userCollege","空");
        editor.putString("userStudentNumber","空");
        editor.apply();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(LogoutActivity.this,MainActivity.class));
        finish();

    }
}
