package com.example.lightdance.appointment.fragments;

import android.app.ProgressDialog;
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

import java.util.List;

import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LightDance on 2017/10/5.
 */

public class NewsFragment extends Fragment{

    //目前是预览，到时候会从数据库读取数据

    private List<NewsBean> newsMsgList;
    private ProgressDialog progressDialog;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //将碎片膨胀成视图(View)
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getActivity());

        //执行初始化数据方法
//        initNews();
        getNews(view);


        return view;
    }

    private void getNews(final View view) {
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
        BmobQuery<NewsBean> query = new BmobQuery<NewsBean>();
        query.setLimit(50);
        query.findObjects(new FindListener<NewsBean>() {
            @Override
            public void done(List<NewsBean> list, BmobException e) {
                newsMsgList = list;
                loadNews(view);
            }
        });
    }

    private void loadNews(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_news);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(getActivity(),newsMsgList);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
    }

    private void initNews() {
        for (int i = 0;i < 5;i++) {
            NewsBean news1 = new NewsBean(R.mipmap.news_pic1, getString(R.string.news_title1),
                    getString(R.string.news_content1));
            newsMsgList.add(news1);
            NewsBean news2 = new NewsBean(R.mipmap.news_pic2, getString(R.string.news_title2),
                    getString(R.string.news_content2));
            newsMsgList.add(news2);
            NewsBean news3 = new NewsBean(R.mipmap.news_pic3, getString(R.string.news_title3),
                    getString(R.string.news_content3));
            newsMsgList.add(news3);
            NewsBean news4 = new NewsBean(R.mipmap.news_pic4, getString(R.string.news_title4),
                    getString(R.string.news_content4));
            newsMsgList.add(news4);
            NewsBean news5 = new NewsBean(R.mipmap.news_pic5, getString(R.string.news_title5),
                    getString(R.string.news_content5));
            newsMsgList.add(news5);
        }
    }
}
