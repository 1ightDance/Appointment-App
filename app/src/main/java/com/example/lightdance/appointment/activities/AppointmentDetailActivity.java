package com.example.lightdance.appointment.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.Model.MemberBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.ParticipantAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class AppointmentDetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_detailed_info_title)
    TextView tvDetailedInfoTitle;
    @BindView(R.id.tv_detailed_info_place)
    TextView tvDetailedInfoPlace;
    @BindView(R.id.tv_detailed_info_starttime)
    TextView tvDetailedInfoStarttime;
    @BindView(R.id.tv_detailed_info_endtime)
    TextView tvDetailedInfoEndtime;
    @BindView(R.id.tv_detailed_info_description)
    TextView tvDetailedInfoDescription;
    @BindView(R.id.tv_detailed_info_connection)
    TextView tvDetailedInfoConnection;
    @BindView(R.id.toolbar_appointmentdetail)
    Toolbar mToolbar;

    private ProgressDialog progressDialog;
    private String objectId;
    private String userNickName;
    private int userAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(AppointmentDetailActivity.this);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        //toolbar
        mToolbar.setTitle("活动详情");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setNavigationIcon(R.mipmap.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences p = getSharedPreferences("loginData",MODE_PRIVATE);
        userNickName = p.getString("nickeName","出错啦啊啊啊~");
        userAvatar = p.getInt("userAvatar",0);

        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");

        BmobQuery<BrowserMsgBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<BrowserMsgBean>() {
            @Override
            public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                loadMsg(browserMsgBean);
                loadParticipant(browserMsgBean.getMembers());
            }
        });
    }

    private void loadParticipant(List<MemberBean> members) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_detailed_info);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        ParticipantAdapter adapter = new ParticipantAdapter(this,members);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
    }


    public void loadMsg(BrowserMsgBean browserMsgBean) {
        tvDetailedInfoTitle.setText(browserMsgBean.getTitle());
        tvDetailedInfoPlace.setText(browserMsgBean.getPlace());
        tvDetailedInfoStarttime.setText(browserMsgBean.getStartTime());
        tvDetailedInfoEndtime.setText(browserMsgBean.getEndTime());
        tvDetailedInfoDescription.setText(browserMsgBean.getContent());
        tvDetailedInfoConnection.setText(browserMsgBean.getContactWay());
    }

    @OnClick({R.id.recyclerView_detailed_info, R.id.detailed_info_take_part_in,R.id.textView18})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recyclerView_detailed_info:
                Intent intent = new Intent(this,MemberDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.textView18:
                Intent intent1 = new Intent(this,MemberDetailActivity.class);
                startActivity(intent1);
                break;
            case R.id.detailed_info_take_part_in:
                progressDialog.show();
                BmobQuery<BrowserMsgBean> query = new BmobQuery<>();
                query.getObject(objectId, new QueryListener<BrowserMsgBean>() {
                    @Override
                    public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                        if (e == null){
                            List<MemberBean> memberBeanList = browserMsgBean.getMembers();
                            int s = memberBeanList.size();
                            int n = browserMsgBean.getPersonNumberHave();
                            int t = browserMsgBean.getTypeCode();
                            SharedPreferences preferences = getSharedPreferences("loginData",MODE_PRIVATE);
                            String userObjectId = preferences.getString("userBeanId","错误啦啊啊啊~");
                            String userNickName = preferences.getString("nickName","错误啦啊啊啊~");
                            int userAvatar = preferences.getInt("userAvatar",0);
                            boolean isJoined = false;
                            for(int i=0;i<s;i++){
                                String s1 = memberBeanList.get(i).getMemberUserBeanId();
                                if (s1.equals(userObjectId)){
                                    isJoined = true;
                                    break;
                                }
                            }
                            if (isJoined){
                                Toast.makeText(AppointmentDetailActivity.this,"别闹..你都已经参加了",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else{
                                BrowserMsgBean browserMsgBean2 = new BrowserMsgBean();
                                List<MemberBean> members= browserMsgBean.getMembers();
                                MemberBean memberBean = new MemberBean();
                                memberBean.setMemberUserBeanId(userObjectId);
                                memberBean.setMemberNickname(userNickName);
                                memberBean.setMemberAvatar(userAvatar);
                                members.add(memberBean);
                                browserMsgBean2.setMembers(members);
                                browserMsgBean2.update(objectId,new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null){
                                        }else{
                                            Toast.makeText(AppointmentDetailActivity.this,"更新数组失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                BrowserMsgBean browserMsgBean1 = new BrowserMsgBean();
                                browserMsgBean1.setPersonNumberHave(n+1);
                                browserMsgBean1.setTypeCode(t);
                                browserMsgBean1.update(objectId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null){
                                            progressDialog.dismiss();
                                        }else{
                                            Toast.makeText(AppointmentDetailActivity.this,"人数更新失败 e="+e.getMessage(),Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                            }
                        }else{
                            Toast.makeText(AppointmentDetailActivity.this,"错误 e="+e.getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                });

                break;
        }
    }
}
