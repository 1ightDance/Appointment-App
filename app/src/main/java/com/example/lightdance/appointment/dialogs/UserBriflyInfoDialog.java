package com.example.lightdance.appointment.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lightdance.appointment.Model.BrowseMsgBean;
import com.example.lightdance.appointment.Model.UserBean;
import com.example.lightdance.appointment.R;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * Created by LightDance on 2017/10/14.
 */

public class UserBriflyInfoDialog extends DialogFragment {

    private ImageView userIcon;
    private TextView userNickName;
    private TextView userCollege;
    private TextView userDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_publisher_info_briefly,container);
        userIcon=(ImageView) view.findViewById(R.id.brief_userinfo_userimg);
        userNickName=(TextView)view.findViewById(R.id.brief_userinfo_usernickname);
        userCollege=(TextView)view.findViewById(R.id.brief_userinfo_usercollege);
        userDescription=(TextView)view.findViewById(R.id.brief_userinfo_userdescription);

        return  view;
    }

    //TODO 从数据库中找数据并设置到Dialog中，但并没有实现获取发帖人昵称的方法
    private void getPublisherMsg(String publisherNickName){
        List<UserBean> publisher = DataSupport.where("userNickName = ?",publisherNickName).find(UserBean.class);
        userIcon.setImageResource(publisher.get(0).getUserIconId());
        userNickName.setText(publisherNickName);
        userCollege.setText(publisher.get(0).getUserCollege());
        userDescription.setText(publisher.get(0).getUserDescription());
    }

    public void showPublisherMsg(View view){
        UserBriflyInfoDialog publisherMsgDialog = new UserBriflyInfoDialog();
        publisherMsgDialog.show(getFragmentManager(),"show user msg");
    }
}

