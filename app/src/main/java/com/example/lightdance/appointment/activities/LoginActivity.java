package com.example.lightdance.appointment.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        tvForgetpassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

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
                editor.apply();
                Intent intent1 = new Intent(this,MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_forgetpassword:
                break;
        }
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
