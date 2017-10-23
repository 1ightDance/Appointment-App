package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;

import org.litepal.crud.DataSupport;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.img_user_background)
    ImageView imgUserBackground;
    @BindView(R.id.img_user_avatar)
    CircleImageView imgUserAvatar;
    @BindView(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @BindView(R.id.tv_user_introduction)
    TextView tvUserIntroduction;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        if (position == 0){
            Toast.makeText(this,"数据错误",Toast.LENGTH_SHORT).show();
        }else{
            userBean = DataSupport.find(UserBean.class,position);
        }
        initMsg(userBean);
    }

    private void initMsg(UserBean userBean) {
        imgUserAvatar.setImageResource(userBean.getUserIconId());
        tvUserNickname.setText(userBean.getUserNickName());
        tvUserIntroduction.setText("        " + userBean.getUserDescription().toString());
    }

    @OnClick({R.id.img_user_background, R.id.img_user_avatar,R.id.img_userinfo_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_user_background:
                Toast.makeText(this,"别乱点",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_user_avatar:
                Toast.makeText(this,"别乱点",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_userinfo_cancel:
                finish();
                break;
        }
    }
}
