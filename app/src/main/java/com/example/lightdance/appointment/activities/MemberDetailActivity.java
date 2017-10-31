package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lightdance.appointment.Model.MemberBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.MemberAdapter;

import java.util.ArrayList;
import java.util.List;

public class MemberDetailActivity extends AppCompatActivity {

    Toolbar mToolbar;

    // TODO 将从后台获取到的成员数据存储到该List中
    private List<MemberBean> memberBeen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

        //toolbar
        mToolbar.setTitle("参与成员");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setNavigationIcon(R.mipmap.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberDetailActivity.this,AppointmentDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_member_detail);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        MemberAdapter adapter = new MemberAdapter(this,memberBeen);
        recyclerView.setAdapter(adapter);

    }
}
