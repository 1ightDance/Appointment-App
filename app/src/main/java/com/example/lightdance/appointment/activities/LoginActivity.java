package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//该Activity用于匹配数据库用户名与密码进行登录，也可由此进入注册页面注册新账号，注册成功后会返回本Activity并自动填表

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editext_account)
    EditText editextAccount;
    @BindView(R.id.edittxt_password)
    EditText edittxtPassword;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.tv_forgetpassword)
    TextView tvForgetpassword;
    @BindView(R.id.toolbar_login)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //toolbar
        mToolbar.setTitle("登录");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setNavigationIcon(R.mipmap.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvForgetpassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    //点击监听
    @OnClick({R.id.btn_sign_up, R.id.btn_sign_in, R.id.tv_forgetpassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_up:
                Intent intent = new Intent(LoginActivity.this ,com.example.lightdance.appointment.activities.SignUpActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.btn_sign_in:
                String studentNum = editextAccount.getText().toString();
                String passWord   = edittxtPassword.getText().toString();
                isMatch(studentNum,passWord);
                break;
            case R.id.tv_forgetpassword:
                break;
        }
    }

    public void isMatch(final String editStudentNum, final String editPassWord){
        BmobQuery<UserBean> query = new BmobQuery<UserBean>();
        query.addWhereEqualTo("userStudentNum",editStudentNum);
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if (list.isEmpty()){
                    Toast.makeText(LoginActivity.this,"该账号不存在",Toast.LENGTH_SHORT).show();
                    return;
                }
                String passWord = list.get(0).getUserPassword();
                String nickName = list.get(0).getUserNickName();
                int userAvatar = list.get(0).getUserIconId();
                String userIntroduction = list.get(0).getUserDescription();
                String userName = list.get(0).getUserName();
                int gender1 = list.get(0).getUserSex();
                String gender = null;
                if (gender1 == 1){
                    gender = "男";
                }if (gender1 == 0){
                    gender = "女";
                }
                String userCollege = list.get(0).getUserCollege();
                if (passWord .equals(editPassWord)){
                    SharedPreferences.Editor editor = getSharedPreferences("loginData",MODE_PRIVATE).edit();
                    editor.putBoolean("isLogined",true);
                    editor.putString("nickName",nickName);
                    editor.putInt("userAvatar",userAvatar);
                    editor.putString("userIntroduction",userIntroduction);
                    editor.putString("userName",userName);
                    editor.putString("userSex",gender);
                    editor.putString("userCollege",userCollege);
                    editor.putString("userStudentNumber",editStudentNum);
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent1);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //重写Back键方法 从登录页返回MainActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (RESULT_OK==resultCode){
                    String newUserId = data.getStringExtra("userStudentNumber");
                    String newUserPassword = data.getStringExtra("userPassword");
                    editextAccount.setText(newUserId);
                    edittxtPassword.setText(newUserPassword);
                }
                break;
            default:
        }
    }
}
