package com.example.lightdance.appointment.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author pope
 */

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.img_user_background)
    ImageView imgUserBackground;
    @BindView(R.id.img_user_avatar)
    CircleImageView imgUserAvatar;
    @BindView(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @BindView(R.id.tv_user_introduction)
    TextView tvUserIntroduction;

    private String objectId;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        //获取跳转至该活动前所点击的用户的UserBean的ObjectId
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        //若不为空则向后台获取该ObjectId对应的用户信息
        if (objectId == null){
            Toast.makeText(this,"数据错误",Toast.LENGTH_SHORT).show();
        }else{
            BmobQuery<UserBean> query = new BmobQuery<>();
            query.getObject(objectId, new QueryListener<UserBean>() {
                @Override
                public void done(UserBean userBean, BmobException e) {
                    initMsg(userBean);
                }
            });
        }
    }

    /**
     * 初始化需要被展示的用户的数据
     * @param userBean 需要被展示的用户对应的UserBean
     */
    private void initMsg(UserBean userBean) {
        imgUserAvatar.setImageResource(userBean.getUserIconId());
        tvUserNickname.setText(userBean.getUserNickName());
        tvUserIntroduction.setText("        "+userBean.getUserDescription());
        progressDialog.dismiss();
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
            default:
                break;
        }
    }
}
