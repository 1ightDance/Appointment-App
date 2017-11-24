package com.example.lightdance.appointment.fragments;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightdance.appointment.Model.TypeBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.BrowserActivity;
import com.example.lightdance.appointment.activities.MainActivity;
import com.example.lightdance.appointment.adapters.TypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment {


    @BindView(R.id.recyclerview_type)
    RecyclerView recyclerviewType;
    Unbinder unbinder;
    @BindView(R.id.new_appointment_main)
    FloatingActionButton fab;

    private List<TypeBean> typeList = new ArrayList<>();
    private int typeCode = 0;

    public TypeFragment() {
        // Required empty public constructor
    }

    public static TypeFragment newInstance() {
        TypeFragment fragment = new TypeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2
                , LinearLayoutManager.VERTICAL, false);
        recyclerviewType.setLayoutManager(layoutManager);
        TypeAdapter adapter = new TypeAdapter(getActivity(), typeList);
        recyclerviewType.setAdapter(adapter);

        recyclerviewType.setOnScrollListener(new HideScrollListener());

        adapter.setTypeItemOnclickListener(new TypeAdapter.OnTypeItemClickListener() {
            @Override
            public void onClick(int position) {
                SharedPreferences preferences = getActivity().getSharedPreferences("loginData", Context.MODE_PRIVATE);
                if (preferences.getBoolean("isLogined", false)) {
                    typeCode = position + 1;
                    Intent intent = new Intent(getActivity(), BrowserActivity.class);
                    intent.putExtra("typeCode", typeCode);
                    intent.putExtra("from",1);
                    startActivity(intent);
                } else {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.changeFragment(6);
                }
            }
        });

        return view;
    }

    /**
     * 类别数据
     */
    private void initData() {
        TypeBean type1 = new TypeBean();
        type1.setTypeImg(R.mipmap.type_study);
        type1.setTypeName("#学习#");
        typeList.add(type1);
        TypeBean type2 = new TypeBean();
        type2.setTypeImg(R.mipmap.type_movies);
        type2.setTypeName("#电影#");
        typeList.add(type2);
        TypeBean type3 = new TypeBean();
        type3.setTypeImg(R.mipmap.type_brpg);
        type3.setTypeName("#桌游#");
        typeList.add(type3);
        TypeBean type4 = new TypeBean();
        type4.setTypeImg(R.mipmap.type_game);
        type4.setTypeName("#电竞#");
        typeList.add(type4);
        TypeBean type5 = new TypeBean();
        type5.setTypeImg(R.mipmap.type_singing);
        type5.setTypeName("#唱歌#");
        typeList.add(type5);
        TypeBean type6 = new TypeBean();
        type6.setTypeImg(R.mipmap.type_sports);
        type6.setTypeName("#运动#");
        typeList.add(type6);
        TypeBean type7 = new TypeBean();
        type7.setTypeImg(R.mipmap.type_dining);
        type7.setTypeName("#吃饭#");
        typeList.add(type7);
        TypeBean type8 = new TypeBean();
        type8.setTypeImg(R.mipmap.type_travel);
        type8.setTypeName("#旅行#");
        typeList.add(type8);
        TypeBean type9 = new TypeBean();
        type9.setTypeImg(R.mipmap.type_others);
        type9.setTypeName("#其它#");
        typeList.add(type9);
    }

    /**
     * 动态隐藏FloatingActionBar判断方法
     */
    class HideScrollListener extends RecyclerView.OnScrollListener {

        private static final int HIDE_HEIGHT = 20;
        private int scrolledInstance = 0;
        private boolean toolbarVisible = true;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            boolean r = (toolbarVisible && dy > 0) || !toolbarVisible && dy < 0;
            if (r) {
                //recycler向上滚动时dy为正，向下滚动时dy为负数
                scrolledInstance += dy;
            }
            if (scrolledInstance > HIDE_HEIGHT && toolbarVisible) {
                //当recycler向上滑动距离超过设置的默认值并且toolbar可见时，隐藏toolbar和fab
                onHide();
                scrolledInstance = 0;
                toolbarVisible = false;
            } else if (scrolledInstance < -HIDE_HEIGHT && !toolbarVisible) {
                //当recycler向下滑动距离超过设置的默认值并且toolbar不可见时，显示toolbar和fab
                onShow();
                scrolledInstance = 0;
                toolbarVisible = true;
            }
        }
    }

    /**
     * 动态隐藏FloatingActionBar方法
     */
    private void onHide() {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
        int marginBottom = params.bottomMargin;
        ObjectAnimator.ofFloat(fab, "translationY", 0, fab.getHeight() + fab.getPaddingBottom() + marginBottom)
                .setDuration(200).start();
    }

    /**
     * 动态显示FloatingActionBar方法
     */
    private void onShow() {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
        int marginBottom = params.bottomMargin;
        ObjectAnimator.ofFloat(fab, "translationY", fab.getHeight() + fab.getPaddingBottom() + marginBottom, 0)
                .setDuration(200).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.new_appointment_main)
    public void onViewClicked() {
        SharedPreferences preferences = getActivity().getSharedPreferences("loginData", Context.MODE_PRIVATE);
        if (preferences.getBoolean("isLogined", false)) {
            Intent intent = new Intent(getActivity(), BrowserActivity.class);
            intent.putExtra("from",2);
            startActivity(intent);
        } else {
            MainActivity activity = (MainActivity) getActivity();
            activity.changeFragment(6);
        }
    }
}
