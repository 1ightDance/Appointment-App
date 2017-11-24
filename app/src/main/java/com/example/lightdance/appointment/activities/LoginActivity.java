package com.example.lightdance.appointment.activities;

import android.app.ProgressDialog;
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

    ProgressDialog progressDialog;

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
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("请稍等");
                progressDialog.setMessage("正在登录...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                isMatch(studentNum,passWord);
                break;
            case R.id.tv_forgetpassword:
                break;
            default:
                break;
        }
    }

    /**
     * 验证账号密码是否正确方法
     * @param editStudentNum 输入框中学号
     * @param editPassWord 输入框中密码
     */
    public void isMatch(final String editStudentNum, final String editPassWord){
        //判空
        if (editPassWord == null||editStudentNum ==null){
            Toast.makeText(LoginActivity.this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        //查询当前输入的学号所对应的密码
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userStudentNum",editStudentNum);
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                //判断账号是否已被创建
                if (list.isEmpty()){
                    Toast.makeText(LoginActivity.this,"该账号未创建",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                //获取密码并判断是否正确
                String passWord = list.get(0).getUserPassword();
                if (passWord .equals(editPassWord)){
                    //若密码正确则获取该用户的所有个人信息并保存至本地 并更改登录状态为已登录
                    String nickName = list.get(0).getUserNickName();
                    int userAvatar = list.get(0).getUserIconId();
                    String userBeanId = list.get(0).getObjectId();
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
                    SharedPreferences.Editor editor = getSharedPreferences("loginData",MODE_PRIVATE).edit();
                    editor.putString("userBeanId",userBeanId);
                    editor.putBoolean("isLogined",true);
                    editor.putString("nickName",nickName);
                    editor.putInt("userAvatar",userAvatar);
                    editor.putString("userIntroduction",userIntroduction);
                    editor.putString("userName",userName);
                    editor.putString("userSex",gender);
                    editor.putString("userCollege",userCollege);
                    editor.putString("userStudentNumber",editStudentNum);
                    editor.apply();
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent1);
                    finish();
                }else{
                    progressDialog.dismiss();
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
