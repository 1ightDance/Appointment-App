package com.example.lightdance.appointment.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.MemberAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author pope
 */

public class MemberDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar2)
    Toolbar mToolbar;

    private String objectId;

    private ProgressDialog progressDialog;

    /**
     * TODO 将从后台获取到的成员数据存储到该List中
     */
    private List<String> memberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        Intent i = getIntent();
        objectId = i.getStringExtra("objectId");

        initMemberData(objectId);

        //toolbar
        mToolbar.setTitle("参与成员");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setNavigationIcon(R.mipmap.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initMemberData(String objectId) {
        BmobQuery<BrowserMsgBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<BrowserMsgBean>() {
            @Override
            public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                memberList = browserMsgBean.getMembers();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_member_detail);
                LinearLayoutManager manager = new LinearLayoutManager(MemberDetailActivity.this);
                recyclerView.setLayoutManager(manager);
                MemberAdapter adapter = new MemberAdapter(MemberDetailActivity.this, memberList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }
        });
    }
}
