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
    private List<MemberBean> memberBeanList;
    private String objectId;

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
        memberBeanList = members;
        ParticipantAdapter adapter = new ParticipantAdapter(this,memberBeanList);
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
                BmobQuery<BrowserMsgBean> query = new BmobQuery<>();
                query.getObject(objectId, new QueryListener<BrowserMsgBean>() {
                    @Override
                    public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                        if (e == null){
                            List<MemberBean> memberBeanList = browserMsgBean.getMembers();
                            int s = memberBeanList.size();
                            SharedPreferences preferences = getSharedPreferences("loginData",MODE_PRIVATE);
                            String userObjectId = preferences.getString("userBeanId","错误啦啊啊啊~");
                            for(int i=0;i<s;i++){
                                String s1 = memberBeanList.get(i).getMemberUserBeanId();
                                if (s1.equals(userObjectId)){
                                    Toast.makeText(AppointmentDetailActivity.this,"别闹..你都已经参加了",Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }else{
                            Toast.makeText(AppointmentDetailActivity.this,"错误 e="+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                break;
        }
    }
}
