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
 */

public class JoinedAppointmentFragment extends Fragment {

    private TextView ifEmpty;
    private TextView sectionLabel;
    private RecyclerView mRecyclerView;
    private String[] objectIds;
    private int count;
    private ProgressDialog progressDialog;
    public static JoinedAppointmentFragment newInstance() {
        JoinedAppointmentFragment fragment = new JoinedAppointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_joined_appointment_history, container, false);
        ifEmpty = (TextView) v.findViewById(R.id.joined_history_if_empty);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.joined_history_section_recyclerview);
        progressDialog = new ProgressDialog(getActivity());
        SharedPreferences preferences = getActivity().getSharedPreferences("loginData", MODE_PRIVATE);
        String loginStudentId = preferences.getString("userBeanId", null);
        count = 0;
        getJoinedAppointmentList(loginStudentId);

        return v;
    }

    /**
     * 下面方法加载数据
     */

    private void getData() {
        //TODO 暂时这样,希望之后能有个每次加载10行，下拉加载更多的逻辑,目前设置的只查50条是有隐患的

        BmobQuery<BrowserMsgBean> msgHistoryList;
        msgHistoryList = new BmobQuery<>();
        SharedPreferences preferences = getActivity().getSharedPreferences("loginData", MODE_PRIVATE);
        String loginStudentId = preferences.getString("userBeanId", null);
        msgHistoryList.addWhereEqualTo("inviter", loginStudentId);
        msgHistoryList.setLimit(50);
        msgHistoryList.findObjects(new FindListener<BrowserMsgBean>() {
            @Override
            public void done(List<BrowserMsgBean> list, BmobException e) {
                /**
                 * * 查询所有约帖中“应约者列表”，显示所有含有本账号的约帖 ...
                 * TODO {@methord setLimit}存在同上隐患
                 */
                BmobQuery<JoinedHistoryBean> joinedHistoryList = new BmobQuery<>();
                SharedPreferences preferences = getActivity().getSharedPreferences("loginData", MODE_PRIVATE);
                String loginStudentId = preferences.getString("userBeanId", null);

                /*找与登录者的objectId相匹配的对应joinedHistoryBean，然后通过这个joinedHistoryBean取得相应的browserIdList
                * 再通过这个browserIdList数组，对BrowserMsgBean表进行查询，找objectId在这个数组中的所有条目*/
                joinedHistoryList.addWhereEqualTo("userObjectId", loginStudentId);
                joinedHistoryList.findObjects(new FindListener<JoinedHistoryBean>() {
                    @Override
                    public void done(List<JoinedHistoryBean> bmobQueryResult, BmobException e) {
                        if (e == null) {
                            //获取对应的那条记录
                            JoinedHistoryBean result = bmobQueryResult.iterator().next();

                            if (result.getBrowserIdList() != null && result.getBrowserIdList().size() > 0) {

                                /*查询结果不为空，通过查到的帖子id列表，向发帖列表中查询对应帖子，并通过适配器显示*/
                                BmobQuery<BrowserMsgBean> msgHistoryList = new BmobQuery<>();
                                List<String> idList = result.getBrowserIdList();
                                String[] idArray = new String[idList.size()];
                                int i = 0;
                                for (Iterator iterator = idList.iterator(); iterator.hasNext(); i++) {
                                    idArray[i] = (String) iterator.next();
                                }
                                msgHistoryList.addWhereContainedIn("objectId", Arrays.asList(idArray));
                                msgHistoryList.findObjects(new FindListener<BrowserMsgBean>() {
                                    @Override
                                    public void done(List<BrowserMsgBean> list, BmobException e) {
                                        if (e == null) {
                                            Log.e("呸", list.iterator().next().getInviter());
                                            //debug
                                            Toast.makeText(getActivity(), "成功", Toast.LENGTH_SHORT).show();
                                            BrowserMsgAdapter adapter = new BrowserMsgAdapter(getActivity(), list);
                                            mRecyclerView.setAdapter(adapter);
                                        } else {
                                            Log.e("略", "略略略");
                                            Log.e("略", getActivity().getLocalClassName() + "失败：" + e.toString());
                                            Toast.makeText(getActivity(), "失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                /*应约列表为空，设置TextView提示*/
                                ifEmpty.setText("应约记录空空如也");
                            }
                        } else {
                            ifEmpty.setText("你网有毛病吧...");
                            Log.e("smile", "错误码：" + e.getErrorCode() + "，错误描述：" + e.getMessage());
                        }
                    }
                });
            }
        });
    }

    /**
     * @param loginStudentId 传入登录者的objectId，在Bmob中找到与之相匹配的对应joinedHistoryBean，
     *                       然后通过这个joinedHistoryBean取得相应的browserIdList，若该list不为空，
     *                       则使用List中的迭代器iterator存到全局变量array中，否则将布局文件中专门准备的textview设置没有应约记录的提示
     *                       以方便后续通过这个数组查询BrowserMsgBean对应数据库表中的信息
     *                       <p>
     *                       TODO 修改传参数方法获取查询结果
     **/
    private void getJoinedAppointmentList(String loginStudentId) {
        progressDialog.setTitle("请稍等1111");
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
                    if (result.getJoinedAppointment() != null && result.getJoinedAppointment().size() > 0) {
                        ifEmpty.setVisibility(View.GONE);
                        List<String> idList = result.getJoinedAppointment();
                        objectIds = new String[idList.size()];
                        int i = 0;
                        for (Iterator iterator = idList.iterator(); iterator.hasNext(); i++) {
                            count = count + 1;
                            objectIds[i] = (String) iterator.next();
                            if (count == idList.size()) {
                                bindBrowserMsgBeans(objectIds);
                            }
                        }
                    } else {
                        ifEmpty.setText("应约记录空空如也");
                        progressDialog.dismiss();
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
                    LinearLayoutManager layoutManager = new LinearLayoutManager(
                            getActivity());
                    mRecyclerView.setLayoutManager(layoutManager);
                    BrowserMsgAdapter adapter = new BrowserMsgAdapter(getActivity(), list);
                    mRecyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                } else {
                    Log.e("略", getActivity().getLocalClassName() + "失败：" + e.toString());
                    Toast.makeText(getActivity(), "失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
