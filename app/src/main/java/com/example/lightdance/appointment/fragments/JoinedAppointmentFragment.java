package com.example.lightdance.appointment.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.Model.JoinedHistoryBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.BrowserMsgAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by LightDance on 2017/12/3.
 */

public class JoinedAppointmentFragment extends Fragment {

    private TextView ifEmpty;
    private TextView sectionLabel;
    private RecyclerView mRecyclerView;
    public static JoinedAppointmentFragment newInstance() {
        JoinedAppointmentFragment fragment = new JoinedAppointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_joined_appointment_history , container , false);
        ifEmpty = (TextView) v.findViewById(R.id.joined_history_if_empty);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.joined_history_section_recyclerview);
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
                /**
                 * * 查询所有约帖中“应约者列表”，显示所有含有本账号的约帖 ...
                 * TODO {@methord setLimit}存在同上隐患
                 */
                BmobQuery<JoinedHistoryBean> joinedHistoryList = new BmobQuery<>();
                SharedPreferences preferences = getActivity().getSharedPreferences("loginData",MODE_PRIVATE);
                String loginStudentId = preferences.getString("userBeanId",null);
                /*先查询应约过的帖子Id，并存个数组*/
                String basicSql = "select browserIdList from JoinedHistoryBean where userObjectId = " + loginStudentId + ";";
                joinedHistoryList.doSQLQuery(basicSql, new SQLQueryListener<JoinedHistoryBean>() {
                    @Override
                    public void done(BmobQueryResult<JoinedHistoryBean> bmobQueryResult, BmobException e) {
                        if(e ==null){
                            List<JoinedHistoryBean> list = bmobQueryResult.getResults();
                            List<String> msgIds = new ArrayList<>();
                            while(list.iterator().hasNext()){
                                msgIds.add(list.iterator().next().getUserObjectId());
                            }
                            if(list!=null && list.size()>0){
                                /*查询结果不为空，通过查到的帖子id列表，向发帖列表中查询对应帖子，并通过适配器显示*/
                                BmobQuery<BrowserMsgBean> msgHistoryList = new BmobQuery<>();
                                msgHistoryList.addWhereContainedIn("objectId" , Arrays.asList(msgIds));
                                msgHistoryList.findObjects(new FindListener<BrowserMsgBean>() {
                                    @Override
                                    public void done(List<BrowserMsgBean> list, BmobException e) {
                                        if(e==null){
                                            //debug
                                            Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
                                            BrowserMsgAdapter adapter = new BrowserMsgAdapter(getActivity(), list);
                                            mRecyclerView.setAdapter(adapter);
                                        }else{
                                            Toast.makeText(getActivity() , "查询失败："+e.toString() , Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                /*应约列表为空，设置TextView提示*/
                                ifEmpty.setText("应约记录空空如也");
                            }
                        }else{
                            ifEmpty.setText("你网有毛病吧...");
                            Log.e("smile", "错误码："+e.getErrorCode()+"，错误描述："+e.getMessage());
                        }
                    }
                });
            }
        });
    }
}
