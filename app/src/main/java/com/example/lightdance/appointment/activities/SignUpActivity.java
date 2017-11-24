package com.example.lightdance.appointment.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SignUpActivity extends AppCompatActivity {
    private static final int MAN = 1;
    private static final int WOMAN = 0;

    private EditText userStudentNumber;
    private EditText userName;
    private EditText userPassword;
    private EditText userPasswordConfirm;
    private EditText userPhoneNumber;
    private EditText userSecurityCode;
    private EditText userNickName;
    private EditText userCollege;
    private Button btnSecurityCode;

    //用于获取性别并保存在int型里面
    private int userSex = 1;
    private RadioGroup userSexRadioGroup;
    private RadioButton userSexMan;
    private RadioButton userSexWoman;

    private Button signUpFinish;
    private SQLiteDatabase db = Connector.getDatabase();
    private MyCountDownTimer myCountDownTimer;
    private ProgressDialog progressDialog;

    private int hahaha;
    private String hint;
    private String btnText;
    private String templateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //初始化随机出现的3套验证码风格的模板
        initString();

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_signup);
        mToolbar.setTitle("注册");
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        mToolbar.setNavigationIcon(R.mipmap.ic_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //初始化视图控件
        initView();
        //性别选择
        userSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == userSexMan.getId()) {
                    userSex = MAN;
                } else {
                    if (checkedId == userSexWoman.getId()) {
                        userSex = WOMAN;
                    }
                }
            }
        });

        //获取验证码
        btnSecurityCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPhoneNumber.getText().length() == 0){
                    Toast.makeText(SignUpActivity.this,"未填写手机号",Toast.LENGTH_SHORT).show();
                }else if (userPhoneNumber.getText().length() < 11 ){
                    Toast.makeText(SignUpActivity.this,"请填写正确手机号",Toast.LENGTH_SHORT).show();
                }else{
                    myCountDownTimer.start();
                    BmobSMS.requestSMSCode(SignUpActivity.this, userPhoneNumber.getText().toString(), templateText, new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
                            if (e == null){
                                Toast.makeText(SignUpActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SignUpActivity.this,"发送失败 "+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        //完成注册
        signUpFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("注册中...");
                progressDialog.setTitle("请稍等");
                progressDialog.show();
                if (checkUserInfo()) {
                    BmobSMS.verifySmsCode(SignUpActivity.this, userPhoneNumber.getText().toString(),
                            userSecurityCode.getText().toString(), new VerifySMSCodeListener() {
                                @Override
                                public void done(cn.bmob.sms.exception.BmobException e) {
                                    if (e == null){
                                        UserBean newUser = new UserBean();
                                        newUser.setUserName(userName.getText().toString());
                                        newUser.setUserPassword(userPassword.getText().toString());
                                        newUser.setUserStudentNum(userStudentNumber.getText().toString());
                                        newUser.setUserPhoneNumber(userPhoneNumber.getText().toString());
                                        newUser.setUserSex(userSex);
                                        newUser.setUserNickName(userNickName.getText().toString());
                                        newUser.setUserDescription("这个人很懒，什么都没说");
                                        newUser.setUserCollege(userCollege.getText().toString());
                                        newUser.setUserIconId(R.mipmap.ic_user);
                                        newUser.save(new SaveListener<String>() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    Toast.makeText(SignUpActivity.this,
                                                            "注册成功！", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent();
                                                    intent.putExtra("userStudentNumber",
                                                            userStudentNumber.getText().toString());
                                                    intent.putExtra("userPassword",
                                                            userPassword.getText().toString());
                                                    setResult(RESULT_OK, intent);
                                                    progressDialog.dismiss();
                                                    finish();
                                                } else {
                                                    Toast.makeText(SignUpActivity.this,
                                                            "服务器端数据存储失败：" + e.getMessage()
                                                            , Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                }
                                            }
                                        });
                                    }else{
                                        Toast.makeText(SignUpActivity.this,
                                                "验证码错误", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }
            }
        });
    }

    /**
     * 初始化3套随机出现的验证码模板
     */
    private void initString() {
        //生成1~3的随机数
        hahaha = (int) (1+Math.random()*3);
        switch (hahaha){
            case 1:
                hint = "密令";
                btnText = "获取密令";
                templateText = "注册验证码模板1";
                break;
            case 2:
                hint = "通行码";
                btnText = "获取通行码";
                templateText = "注册验证码模板2";
                break;
            case 3:
                hint = "接头暗号";
                btnText = "获取暗号";
                templateText = "注册验证码模板3";
                break;
            default:break;
        }
    }


    /**
     * initView用于成员变量绑定xml文件对应组件
     */
    private void initView() {
        userStudentNumber = (EditText) findViewById(R.id.signup_user_student_num);
        userNickName = (EditText) findViewById(R.id.signup_user_nickname);
        userCollege = (EditText) findViewById(R.id.signup_user_college);
        userName = (EditText) findViewById(R.id.signup_user_name);
        userPassword = (EditText) findViewById(R.id.signup_user_password);
        userPasswordConfirm = (EditText) findViewById(R.id.signup_user_ensure_password);
        userPhoneNumber = (EditText) findViewById(R.id.signup_user_phone_num);
        userSexRadioGroup = (RadioGroup) findViewById(R.id.signup_user_sex_radiogroup);
        userSexMan = (RadioButton) findViewById(R.id.signup_user_sex_man);
        userSexWoman = (RadioButton) findViewById(R.id.signup_user_sex_woman);
        signUpFinish = (Button) findViewById(R.id.signup_finish);
        userSecurityCode = (EditText) findViewById(R.id.signup_security_code);
        btnSecurityCode = (Button) findViewById(R.id.btn_security_code);
        userSecurityCode.setHint(hint);
        btnSecurityCode.setText(btnText);
        progressDialog = new ProgressDialog(this);
        myCountDownTimer = new MyCountDownTimer(60000,1000);
    }

    //复写倒计时控件
    public class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long millisUntilFinished) {
            btnSecurityCode.setClickable(false);
            btnSecurityCode.setText(millisUntilFinished/1000+"s");
            btnSecurityCode.setHeight(32);
            btnSecurityCode.setBackgroundResource(R.drawable.bg_btn_press);
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            btnSecurityCode.setText("重新获取");
            btnSecurityCode.setClickable(true);
            btnSecurityCode.setHeight(32);
            btnSecurityCode.setBackgroundResource(R.drawable.bg_btn);
        }
    }

    /**
     * checkUserInfo用来检测用户输入信息是否合法、是否缺少必要信息
     * @return 如果合法返回true,否则返回false
     */
    private boolean checkUserInfo() {
        if (userStudentNumber.getText().toString().length() < 8) {
            Toast.makeText(SignUpActivity.this, "学号长度小于八位！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (userPassword.getText().toString().length() < 8) {
            Toast.makeText(SignUpActivity.this, "密码长度小于八位！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!(userPassword.getText().toString().equals(userPasswordConfirm.getText().toString()))) {
            Toast.makeText(SignUpActivity.this, "密码输入不一致！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (userPhoneNumber.getText().toString().length() < 7) {
            Toast.makeText(SignUpActivity.this, "请输入正确的电话号码！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (userSecurityCode.getText() == null) {
            Toast.makeText(SignUpActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }else{
                return true;
        }
    }
}
