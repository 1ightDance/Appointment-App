package com.example.lightdance.appointment.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.AppointmentHistoryActivity;
import com.example.lightdance.appointment.activities.LoginActivity;
import com.example.lightdance.appointment.activities.LogoutActivity;
import com.example.lightdance.appointment.activities.PersonalInformationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends Fragment {

    @BindView(R.id.user_background)
    ImageView userBackground;
    @BindView(R.id.user_avatar)
    ImageView userAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_information)
    TextView tvInformation;
    @BindView(R.id.img_infor_next)
    ImageView imgInforNext;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.img_history_next)
    ImageView imgHistoryNext;
    @BindView(R.id.tv_settings)
    TextView tvSettings;
    @BindView(R.id.img_settings_next)
    ImageView imgSettingsNext;
    @BindView(R.id.tv_help)
    TextView tvHelp;
    @BindView(R.id.img_help_next)
    ImageView imgHelpNext;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.img_about_next)
    ImageView imgAboutNext;
    Unbinder unbinder;

    private boolean isLogined = false;
    private String userObjectId;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }

    public static PersonalCenterFragment newInstance() {
        PersonalCenterFragment fragment = new PersonalCenterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将碎片膨胀成视图(View)
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        SharedPreferences preferences = getActivity().getSharedPreferences("loginData", Context.MODE_PRIVATE);
        isLogined = preferences.getBoolean("isLogined", false);
        if (isLogined){
            userObjectId = preferences.getString("userBeanId","出错啦~");
        }
        checkIsLogined(isLogined);
        return view;
    }

    /**
     * 判断是否登录而更改个人中心碎片内容
     *
     * @param logined 是否登录
     */
    public void checkIsLogined(boolean logined) {
        if (logined == false) {
            tvInformation.setTextColor(Color.parseColor("#ff757575"));
            tvHistory.setTextColor(Color.parseColor("#ff757575"));
        }
        if (logined == true) {
            BmobQuery<UserBean> q = new BmobQuery<>();
            q.getObject(userObjectId, new QueryListener<UserBean>() {
                @Override
                public void done(UserBean userBean, BmobException e) {
                    if (e == null){
                        userAvatar.setImageResource(userBean.getUserIconId());
                    }else{
                        Toast.makeText(getActivity(),"加载头像出错"+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
            SharedPreferences preferences = getActivity().getSharedPreferences("loginData", Context.MODE_PRIVATE);
            tvUserName.setText(preferences.getString("nickName", "点击登录"));
        }
        imgInforNext.setClickable(logined);
        imgHistoryNext.setClickable(logined);
        tvInformation.setClickable(logined);
        tvHistory.setClickable(logined);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_background, R.id.user_avatar, R.id.tv_user_name, R.id.tv_information
            , R.id.img_infor_next, R.id.tv_history, R.id.img_history_next, R.id.tv_settings
            , R.id.img_settings_next, R.id.tv_help, R.id.img_help_next, R.id.tv_about
            , R.id.img_about_next, R.id.img_count_setting})
    public void onViewClicked(View view) {

        Intent intent = null;

        switch (view.getId()) {
            case R.id.user_background:
                intent = null;
                break;
            case R.id.user_avatar:
                intent = null;
                break;
            case R.id.tv_user_name:
                if (isLogined) {
                    startActivity(new Intent(getActivity(), LogoutActivity.class));
                    getActivity().finish();
                    break;
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().finish();
                }
                break;
            case R.id.tv_information:
                intent = new Intent(getActivity(), PersonalInformationActivity.class);
                break;
            case R.id.img_infor_next:
                intent = new Intent(getActivity(), PersonalInformationActivity.class);
                break;
            case R.id.tv_history:
                intent = new Intent(getActivity(), AppointmentHistoryActivity.class);
                break;
            case R.id.img_history_next:
                intent = new Intent(getActivity(), AppointmentHistoryActivity.class);
                break;
            case R.id.tv_settings:
                intent = null;
                break;
            case R.id.img_settings_next:
                intent = null;
                break;
            case R.id.tv_help:
                intent = null;
                break;
            case R.id.img_help_next:
                intent = null;
                break;
            case R.id.tv_about:
                intent = null;
                break;
            case R.id.img_about_next:
                intent = null;
                break;
            case R.id.img_count_setting:
                if (isLogined) {
                    startActivity(new Intent(getActivity(), LogoutActivity.class));
                    getActivity().finish();
                    break;
                } else {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().finish();
                }
                break;
            default:
                break;
        }
        if (intent == null) {
            return;
        } else {
            startActivity(intent);
        }
    }
}
