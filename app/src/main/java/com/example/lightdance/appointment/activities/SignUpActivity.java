package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;

import org.litepal.tablemanager.Connector;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SignUpActivity extends AppCompatActivity {
    private static final int MAN=1;
    private static final int WOMAN=0;

    private EditText userStudentNumber;
    private EditText userName;
    private EditText userPassword;
    private EditText userPasswordConfirm;
    private EditText userPhoneNumber;

    //用于获取性别并保存在int型里面
    private int userSex = 1;
    private RadioGroup userSexRadioGroup;
    private RadioButton userSexMan;
    private RadioButton userSexWoman;

    private Button signUpFinish;
    private SQLiteDatabase db=Connector.getDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_signup);
        mToolbar.setTitle("注册");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setNavigationIcon(R.mipmap.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initView();
        userSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId==userSexMan.getId())
                    userSex=MAN;
                else if (checkedId==userSexWoman.getId())
                    userSex=WOMAN;
            }
        });
        signUpFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkUserInfo()){
                    UserBean newUser=new UserBean();
                    newUser.setUserName(userName.getText().toString());
                    newUser.setUserPassword(userPassword.getText().toString());
                    newUser.setUserStudentNum(userStudentNumber.getText().toString());
                    newUser.setUserPhoneNumber(userPhoneNumber.getText().toString());
                    newUser.setUserSex(userSex);
                    newUser.setUserNickName(userName.getText().toString());
                    newUser.setUserDescription("这个人很懒，什么都没说");
                    newUser.setUserCollege("未填");
                    newUser.setUserIconId(R.mipmap.ic_user);
                    newUser.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(SignUpActivity.this,"服务器端数据存储成功 ObjectId = " + s
                                        ,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SignUpActivity.this,"服务器端数据存储失败：" + e.getMessage()
                                        ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(SignUpActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.putExtra("userStudentNumber",newUser.getUserStudentNum());
                    intent.putExtra("userPassword",newUser.getUserPassword());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }


    //initView用于成员变量绑定xml文件对应组件
    private void initView(){
        userStudentNumber=(EditText)findViewById(R.id.signup_user_student_num);
        userName=(EditText)findViewById(R.id.signup_user_name);
        userPassword=(EditText)findViewById(R.id.signup_user_password);
        userPasswordConfirm=(EditText)findViewById(R.id.signup_user_ensure_password);
        userPhoneNumber=(EditText)findViewById(R.id.signup_user_phone_num);
        userSexRadioGroup=(RadioGroup)findViewById(R.id.signup_user_sex_radiogroup);
        userSexMan=(RadioButton)findViewById(R.id.signup_user_sex_man);
        userSexWoman=(RadioButton)findViewById(R.id.signup_user_sex_woman);
        signUpFinish=(Button)findViewById(R.id.signup_finish);
    }

    //checkUserInfo用来检测用户输入信息是否合法、是否缺少必要信息
    private boolean checkUserInfo(){
        if (userStudentNumber.getText().toString().length()<8){
            Toast.makeText(SignUpActivity.this,"学号长度小于八位！",Toast.LENGTH_SHORT).show();
            return false;
        }else if (userPassword.getText().toString().length()<8){
            Toast.makeText(SignUpActivity.this,"密码长度小于八位！",Toast.LENGTH_SHORT).show();
            return false;
        }else if (!(userPassword.getText().toString().equals(userPasswordConfirm.getText().toString()))){
            Toast.makeText(SignUpActivity.this,"密码输入不一致！",Toast.LENGTH_SHORT).show();
            return false;
        }else if (userPhoneNumber.getText().toString().length()<7){
            Toast.makeText(SignUpActivity.this,"请输入正确的电话号码！",Toast.LENGTH_SHORT).show();
            return false;
        }else
            return true;
    }

}
