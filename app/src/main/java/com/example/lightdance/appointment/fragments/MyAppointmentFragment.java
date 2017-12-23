package com.example.lightdance.appointment.fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.Model.HistoryBean;
import com.example.lightdance.appointment.Model.JoinedHistoryBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.BrowserMsgAdapter;

import java.util.Arrays;
import java.util.Iterator;
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
    private RecyclerView mRecyclerView;
    private String[] objectIds;
    private int count;
    private ProgressDialog progressDialog;
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
        SharedPreferences preferences = getActivity().getSharedPreferences("loginData", MODE_PRIVATE);
        String loginStudentId = preferences.getString("userBeanId", null);
        count = 0;
        progressDialog = new ProgressDialog(getActivity());
        getMyAppointmentList(loginStudentId);
        return v;
    }

    /**
     * 下面方法加载数据
     */
    private void getMyAppointmentList(String loginStudentId) {

        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        BmobQuery<HistoryBean> joinedHistoryList = new BmobQuery<>();
        joinedHistoryList.addWhereEqualTo("userObjectId", loginStudentId);
        joinedHistoryList.findObjects(new FindListener<HistoryBean>() {
            @Override
            public void done(List<HistoryBean> bmobQueryResult, BmobException e) {
                if (e == null) {
                    //获取对应的记录id,目前只有未结束的活动
                    HistoryBean result = bmobQueryResult.iterator().next();
                    if (result.getOrganizeAppointment() != null && result.getOrganizeAppointment().size() > 0) {
                        ifEmpty.setVisibility(View.GONE);
                        List<String> idList = result.getOrganizeAppointment();
                        objectIds = new String[idList.size()];
                        int i = 0;
                        for (Iterator iterator = idList.iterator(); iterator.hasNext(); i++) {
                            count = count + 1;
                            objectIds[i] = (String) iterator.next();
                            Log.e("来看数组", objectIds[i]);
                            if (count == idList.size()) {
                                Log.e("来看数组", objectIds[0]);
                                bindBrowserMsgBeans(objectIds);
                            }
                        }
                    } else {
                        ifEmpty.setText("应约记录空空如也");
                    }
                } else {
                    ifEmpty.setText("你网有毛病吧...");
                    Log.e("smile", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                }
            }
        });
    }

    private void bindBrowserMsgBeans(String[] browserMsgObjectIds) {
        BmobQuery<BrowserMsgBean> msgHistoryList = new BmobQuery<>();
        msgHistoryList.addWhereContainedIn("objectId", Arrays.asList(browserMsgObjectIds));
        msgHistoryList.findObjects(new FindListener<BrowserMsgBean>() {
            @Override
            public void done(List<BrowserMsgBean> list, BmobException e) {
                if (e == null) {
                    Log.e("呸", list.iterator().next().getInviter());
                    //debug
                    Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                            getActivity());
                    mRecyclerView.setLayoutManager(layoutManager);
                    BrowserMsgAdapter adapter = new BrowserMsgAdapter(getActivity(), list);
                    mRecyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                } else {
                    Log.e("略", "略略略");
                    Log.e("略", getActivity().getLocalClassName() + "失败：" + e.toString());
                    Toast.makeText(getActivity(), "失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
