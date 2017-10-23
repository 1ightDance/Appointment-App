package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowseMsgBean;
import com.example.lightdance.appointment.R;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    private BrowseMsgBean clickedMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        ButterKnife.bind(this);

        //toolbar
        mToolbar.setTitle("活动详情");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setNavigationIcon(R.mipmap.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentDetailActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        if (position == 0){
            Toast.makeText(this,"数据错误",Toast.LENGTH_SHORT).show();
        }else{
            clickedMsg = DataSupport.find(BrowseMsgBean.class,position);
        }
        initMsg(clickedMsg);

    }

    @OnClick(R.id.detailed_info_take_part_in)
    public void onViewClicked() {
        Toast.makeText(this,"尚未开发",Toast.LENGTH_SHORT).show();
    }

    public void initMsg(BrowseMsgBean browseMsgBean) {
        tvDetailedInfoTitle.setText(browseMsgBean.getTitle());
        tvDetailedInfoPlace.setText(browseMsgBean.getPlace());
        tvDetailedInfoStarttime.setText(browseMsgBean.getStartTime());
        tvDetailedInfoEndtime.setText(browseMsgBean.getEndTime());
        tvDetailedInfoDescription.setText(browseMsgBean.getContent());
        tvDetailedInfoConnection.setText(browseMsgBean.getContactWay());
    }

}
