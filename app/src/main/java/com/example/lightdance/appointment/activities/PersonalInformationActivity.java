package com.example.lightdance.appointment.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    protected static final int CHOOSE_PICTURE = 0;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;
    private Bitmap mBitmap;

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
                Intent openAlbumIntent = new Intent(
                        Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
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
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(PersonalInformationActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("alanjet", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            //这里图片是方形的，可以用一个工具类处理成圆形（很多头像都是圆形，这种工具类网上很多不再详述）
            imgUserinforAvatar.setImageBitmap(mBitmap);//显示图片
            //在这个地方可以写上上传该图片到服务器的代码，后期将单独写一篇这方面的博客，敬请期待...
        }
    }

}
