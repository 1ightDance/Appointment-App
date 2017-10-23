package com.example.lightdance.appointment.fragments;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviterInfoFragment extends DialogFragment {


    @BindView(R.id.img_brief_userinfo_userimg)
    ImageView imgBriefUserinfoUserimg;
    @BindView(R.id.tv_brief_userinfo_usernickname)
    TextView tvBriefUserinfoUsernickname;
    @BindView(R.id.tv_brief_userinfo_userdescription)
    TextView tvBriefUserinfoUserdescription;
    @BindView(R.id.cardView2)
    CardView cardView2;

    int userAvatarId;
    String nickName;
    String introduction;

    public InviterInfoFragment() {
        // Required empty public constructor
    }

    public static InviterInfoFragment newInstance() {
        InviterInfoFragment fragment = new InviterInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inviter_info, container, false);
        ButterKnife.bind(this, view);

        initMsg();

        return view;
    }

    private void initMsg() {
        tvBriefUserinfoUsernickname.setText(nickName);
        tvBriefUserinfoUserdescription.setText(introduction);
        imgBriefUserinfoUserimg.setImageResource(userAvatarId);
    }

    @OnClick(R.id.img_brief_userinfo_cancel)
    public void onViewClicked() {
        dismiss();
    }

    public void setMsg(int id,String name,String introduction){
        userAvatarId = id;
        nickName = name;
        this.introduction = introduction;
    }

}
