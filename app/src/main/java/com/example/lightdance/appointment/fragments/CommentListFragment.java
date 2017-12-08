package com.example.lightdance.appointment.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.Model.HistoryBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.CommentActivity;
import com.example.lightdance.appointment.adapters.NoCommentListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author pope
 *         A simple {@link Fragment} subclass.
 */
public class CommentListFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.toolbar_comment_list_fragment)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_comment_list_fragment)
    RecyclerView recyclerView;

    private List<BrowserMsgBean> browserMsgBeen;
    private ProgressDialog progressDialog;

    public CommentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        toolbar.setTitle("未反馈活动列表");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity commentActivity = (CommentActivity) getActivity();
                commentActivity.finish();
            }
        });

        progressDialog = new ProgressDialog(getActivity());

//        initData();

        return view;
    }

    private void initData() {
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("加载中...");
        progressDialog.show();
        browserMsgBeen = new ArrayList<>();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginData", Context.MODE_PRIVATE);
        String userObjectId = sharedPreferences.getString("userBeanId", "出错啦~");
        BmobQuery<HistoryBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userObjectId", userObjectId);
        query.findObjects(new FindListener<HistoryBean>() {
            @Override
            public void done(List<HistoryBean> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        final List<String> browserId = list.get(0).getNoComment();
                        for (int i = 0; i < browserId.size(); i++) {
                            Log.i("调试","第"+i+"调用");
                            final int finalI = i;
                            BmobQuery<BrowserMsgBean> query1 = new BmobQuery<>();
                            Log.i("调试","第"+i+"次准备查询");
                            query1.getObject(browserId.get(i), new QueryListener<BrowserMsgBean>() {
                                @Override
                                public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                                    Log.i("调试","第"+finalI+"次查询成功");
                                    if (e == null){
                                        Log.i("调试","第"+finalI+"次准备添加");
                                        browserMsgBeen.add(finalI,browserMsgBean);
                                        Log.i("调试","第"+finalI+"次添加结束");
                                        if (finalI == browserId.size()-1){
                                            Log.i("调试","结束循环并加载List");
                                            initList();
                                        }
                                    }else{
                                        Toast.makeText(getActivity(), "查询数据出错1" + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "查询数据出错2" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NoCommentListAdapter adapter = new NoCommentListAdapter(browserMsgBeen, getActivity());
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
    }

    public static CommentListFragment newInstance() {
        CommentListFragment fragment = new CommentListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
