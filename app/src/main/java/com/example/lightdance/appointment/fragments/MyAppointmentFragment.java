package com.example.lightdance.appointment.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.BrowserMsgAdapter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by LightDance on 2017/12/3.
 * 历史记录中的“我的约帖”模块，缺少一个“刷新约帖记录”方法
 * 对应{@link com.example.lightdance.appointment.activities.AppointmentHistoryActivity}中
 * {@param ARG_SECTION_NUMBER}为1的情况
 */

public class MyAppointmentFragment extends Fragment {

    private TextView ifEmpty;
    private TextView sectionLabel;
    private RecyclerView mRecyclerView;
    public static MyAppointmentFragment newInstance() {
        MyAppointmentFragment fragment = new MyAppointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_my_appointment_history , container , false);
        ifEmpty = (TextView) v.findViewById(R.id.history_if_empty);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.section_recyclerview);
        getData();
        return v;
    }

    /**
     * 下面方法加载数据
     */

    private void getData(){
        //TODO 暂时这样,希望之后能有个每次加载10行，下拉加载更多的逻辑,目前设置的只查50条是有隐患的

        BmobQuery<BrowserMsgBean> msgHistoryList;
        msgHistoryList = new BmobQuery<>();
        SharedPreferences preferences = getActivity().getSharedPreferences("loginData",MODE_PRIVATE);
        String loginStudentId = preferences.getString("userBeanId",null);
        msgHistoryList.addWhereEqualTo("inviter" , loginStudentId);
        msgHistoryList.setLimit(50);
        msgHistoryList.findObjects(new FindListener<BrowserMsgBean>() {
            @Override
            public void done(List<BrowserMsgBean> list, BmobException e) {
                if (e == null) {
                    //查询成功，读取数据以供adapter保存
                    //Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();//debug
                    BrowserMsgAdapter adapter = new BrowserMsgAdapter(getActivity(), list);
                    mRecyclerView.setAdapter(adapter);
                    //如果为空，显示这样的字段
                    if (list.isEmpty()) {
                        ifEmpty.setText("发布记录空空如也");
                    }
                } else {
                    //TODO 这里在考虑加幅网络连接失败图片啥的，但是不太会搞定那个抛异常
                    Toast.makeText(getActivity(), "你网有毛病吧"
                            +e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
