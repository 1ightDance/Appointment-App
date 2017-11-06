package com.example.lightdance.appointment.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.fragments.SetPersonalInformationFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class PersonalInformationActivity extends AppCompatActivity {

    //利用黄油刀完成的FindViewById
    @BindView(R.id.toolbar_userinfor)
    Toolbar toolbar;
    @BindView(R.id.tv_userinfor_nickname)
    TextView tvUserinforNickname;
    @BindView(R.id.img_userinfor_avatar)
    ImageView imgUserinforAvatar;
    @BindView(R.id.editText_userinfor_introduction)
    EditText editTextUserinforIntroduction;
    @BindView(R.id.tv_userinfor_name)
    TextView tvUserinforName;
    @BindView(R.id.tv_userinfor_sex)
    TextView tvUserinforSex;
    @BindView(R.id.tv_userinfor_college)
    TextView tvUserinforCollege;
    @BindView(R.id.tv_userinfor_studentnumb)
    TextView tvUserinforStudentnumb;

    private SetPersonalInformationFragment editDialog = new SetPersonalInformationFragment();
    private int setCode = 0;
    private int SET_NICKNAME = 1;
    private int SET_COLLEGE = 2;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        ButterKnife.bind(this);

        toolbar.setTitle("个人信息");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initInformation();

    }

    private void initInformation() {
        SharedPreferences p = getSharedPreferences("loginData",MODE_PRIVATE);
        tvUserinforNickname.setText(p.getString("nickName","无"));
        imgUserinforAvatar.setImageResource(p.getInt("userAvatar",0));
        editTextUserinforIntroduction.setText(p.getString("userIntroduction","空"));
        tvUserinforName.setText(p.getString("userName","无"));
        tvUserinforSex.setText(p.getString("userSex","无"));
        tvUserinforCollege.setText(p.getString("userCollege","无"));
        tvUserinforStudentnumb.setText(p.getString("userStudentNumber","无"));
    }

    //XML中控件的点击监听
    @OnClick({ R.id.tv_userinfor_nickname, R.id.img_userinfor_avatar, R.id.tv_userinfor_name, R.id.tv_userinfor_sex, R.id.tv_userinfor_college, R.id.tv_userinfor_studentnumb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_userinfor_nickname:
                editDialog.show(getSupportFragmentManager(),"editDialog");
                setCode = SET_NICKNAME;
                break;
            case R.id.img_userinfor_avatar:
                Toast.makeText(this,"正在解锁ing...",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_userinfor_name:
                Toast.makeText(this,"姓名无法编辑",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_userinfor_sex:
                Toast.makeText(this,"性别无法编辑",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_userinfor_college:
                editDialog.show(getSupportFragmentManager(),"editDialog");
                setCode = SET_COLLEGE;
                break;
            case R.id.tv_userinfor_studentnumb:
                Toast.makeText(this,"学号无法编辑",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void setMsg(String s){
        if (setCode == SET_NICKNAME){
            setNickName(s);
        }
        if (setCode == SET_COLLEGE){
            setCollege(s);
        }
    }

    public void setNickName(String s){
        tvUserinforNickname.setText(s);
    }

    public void setCollege(String s){
        tvUserinforCollege.setText(s);
    }

    public void updateData(final String s){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在保存...");
        progressDialog.setCancelable(false);
        SharedPreferences p = getSharedPreferences("loginData", Context.MODE_PRIVATE);
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userStudentNum",p.getString("userStudentNumber","错误"));
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if (e == null){
                    SharedPreferences.Editor editor =getSharedPreferences("loginData",MODE_PRIVATE).edit();
                    String objectId = list.get(0).getObjectId();
                    UserBean user = new UserBean();
                    if (setCode == SET_NICKNAME){
                        editor.putString("nickName",s);
                        editor.apply();
                        user.setUserNickName(s);
                        user.update(objectId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null){
                                    Toast.makeText(PersonalInformationActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(PersonalInformationActivity.this,"保存失败"+e.getMessage()
                                            ,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
//                        list.get(0).setValue("userNickName",s);
                        progressDialog.dismiss();
                    }
                    if (setCode == SET_COLLEGE){
                        editor.putString("userCollege",s);
                        editor.apply();
                        user.setUserCollege(s);
                        user.update(objectId, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null){
                                    Toast.makeText(PersonalInformationActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(PersonalInformationActivity.this, "保存失败" + e.getMessage()
                                            , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
//                        list.get(0).setValue("userCollege",s);
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(PersonalInformationActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
