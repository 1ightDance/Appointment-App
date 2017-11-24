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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author pope
 */

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("加载中...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        SharedPreferences p = getSharedPreferences("loginData",MODE_PRIVATE);
        String objectId = p.getString("userBeanId","7777777");
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean userBean, BmobException e) {
                if (e == null){
                    tvUserinforNickname.setText(userBean.getUserNickName());
                    imgUserinforAvatar.setImageResource(userBean.getUserIconId());
                    editTextUserinforIntroduction.setText(userBean.getUserDescription());
                    tvUserinforName.setText(userBean.getUserName());
                    String sex = null;
                    if (userBean.getUserSex() == 0){
                        sex = "女";
                    }else{
                        sex = "男";
                    }
                    tvUserinforSex.setText(sex);
                    tvUserinforCollege.setText(userBean.getUserCollege());
                    tvUserinforStudentnumb.setText(userBean.getUserStudentNum());
                    progressDialog.dismiss();
                }else{
                    Toast.makeText(PersonalInformationActivity.this,"数据获取出错"+e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    //XML中控件的点击监听
    @OnClick({ R.id.tv_userinfor_nickname, R.id.img_userinfor_avatar, R.id.tv_userinfor_name,
            R.id.tv_userinfor_sex, R.id.tv_userinfor_college, R.id.tv_userinfor_studentnumb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_userinfor_nickname:
                editDialog.show(getSupportFragmentManager(),"editDialog");
                setCode = SET_NICKNAME;
                break;
            case R.id.img_userinfor_avatar:
                Toast.makeText(this,"休想换头像",Toast.LENGTH_SHORT).show();
//                Intent openAlbumIntent = new Intent(
//                        Intent.ACTION_GET_CONTENT);
//                openAlbumIntent.setType("image/*");
                /**用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                 * 暂时不提供更换头像功能
                 * 相关已写功能代码暂时注释掉
                 */
//                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                break;
            case R.id.tv_userinfor_name:
                Toast.makeText(this,"名字已定，无法更改",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_userinfor_sex:
                Toast.makeText(this,"哟？你要变性了啊？",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_userinfor_college:
                editDialog.show(getSupportFragmentManager(),"editDialog");
                setCode = SET_COLLEGE;
                break;
            case R.id.tv_userinfor_studentnumb:
                Toast.makeText(this,"学号肯定不会给你改的，小样",Toast.LENGTH_SHORT).show();
                break;
            default:break;
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

    /**
     * 更新数据方法
     * @param s 企图更新的新数据
     */
    public void updateData(final String s){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在保存...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        //通过获取SharedPreferences里的userBeanId获取当前用户的ObjectId查询表数据
        SharedPreferences p = getSharedPreferences("loginData", Context.MODE_PRIVATE);
        final String objectId = p.getString("userBeanId","错误");
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<UserBean>() {
            @Override
            public void done(UserBean user, BmobException e) {
                //更新昵称数据
                if (setCode == SET_NICKNAME){
                    user.setValue("userNickName",s);
                    user.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                tvUserinforNickname.setText(s);
                                SharedPreferences.Editor editor = getSharedPreferences("loginData",
                                        Context.MODE_PRIVATE).edit();
                                editor.putString("nickName",s);
                                Toast.makeText(PersonalInformationActivity.this,"保存成功",
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else{
                                Toast.makeText(PersonalInformationActivity.this,"保存失败"+e.getMessage()
                                        ,Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
                //更新学院数据
                if (setCode == SET_COLLEGE){
                    user.setValue("userCollege",s);
                    user.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                tvUserinforCollege.setText(s);
                                SharedPreferences.Editor editor = getSharedPreferences("loginData",
                                        Context.MODE_PRIVATE).edit();
                                editor.putString("userCollege",s);
                                Toast.makeText(PersonalInformationActivity.this,"保存成功",
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else {
                                Toast.makeText(PersonalInformationActivity.this, "保存失败" + e.getMessage()
                                        , Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
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
                default:
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
