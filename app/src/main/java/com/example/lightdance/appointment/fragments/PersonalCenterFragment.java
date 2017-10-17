package com.example.lightdance.appointment.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.LoginActivity;
import com.example.lightdance.appointment.activities.PersonalInformationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.img_notice_next)
    ImageView imgNoticeNext;
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
        // Inflate the layout for this fragment
        //将碎片膨胀成视图(View)
        View view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        checkIsLogined(isLogined);
        return view;
    }

    public void checkIsLogined(boolean logined) {
            tvInformation.setClickable(logined);
            tvNotice.setClickable(logined);
            tvHistory.setClickable(logined);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_background, R.id.user_avatar, R.id.tv_user_name, R.id.tv_information, R.id.img_infor_next, R.id.tv_history, R.id.img_history_next, R.id.tv_settings, R.id.img_settings_next, R.id.tv_notice, R.id.img_notice_next, R.id.tv_help, R.id.img_help_next, R.id.tv_about, R.id.img_about_next})
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
                intent = new Intent(getActivity(), LoginActivity.class);
                break;
            case R.id.tv_information:
                intent = new Intent(getActivity(), PersonalInformationActivity.class);
                break;
            case R.id.img_infor_next:
                intent = new Intent(getActivity(), PersonalInformationActivity.class);
                break;
            case R.id.tv_history:
                intent = null;
                break;
            case R.id.img_history_next:
                intent = null;
                break;
            case R.id.tv_settings:
                intent = null;
                break;
            case R.id.img_settings_next:
                intent = null;
                break;
            case R.id.tv_notice:
                intent = null;
                break;
            case R.id.img_notice_next:
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
        }
        startActivity(intent);
    }
}
