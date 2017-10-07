package com.example.lightdance.appointment.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightdance.appointment.Model.NewsBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by LightDance on 2017/10/5.
 */

public class NewsFragment extends Fragment{

    //目前是预览，到时候会从数据库读取数据

    private List<NewsBean> newsMsgList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //将碎片膨胀成视图(View)
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        //执行初始化数据方法
        initNews();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(newsMsgList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initNews() {
        NewsBean news1 = new NewsBean(R.mipmap.news_pic1,getString(R.string.news_title1),
                getString(R.string.news_content1));
        newsMsgList.add(news1);
        NewsBean news2 = new NewsBean(R.mipmap.news_pic2,getString(R.string.news_title2),
                getString(R.string.news_content2));
        newsMsgList.add(news2);
    }
}
