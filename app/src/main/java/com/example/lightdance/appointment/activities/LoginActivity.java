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

import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                SharedPreferences.Editor editor = getSharedPreferences("loginData",MODE_PRIVATE).edit();
                editor.putBoolean("isLogined",true);
                editor.putString("nickName","教皇");
                editor.putInt("userAvatar",R.mipmap.headshot_2);
                editor.putString("userIntroduction","狼人杀路人王，你怕了吗？");
                editor.putString("userName","李黄旗");
                editor.putString("userSex","男");
                editor.putString("userCollege","通信工程学院");
                editor.putString("userStudentNumb","15083111");
                editor.apply();
                Intent intent1 = new Intent(this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.tv_forgetpassword:
                break;
        }
    }

    //重写Back键方法 从登录页返回MainActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
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
