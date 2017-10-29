package com.example.lightdance.appointment.fragments;


import android.content.Intent;
import android.os.Bundle;
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
import com.example.lightdance.appointment.adapters.TypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class TypeFragment extends Fragment {


    @BindView(R.id.recyclerview_type)
    RecyclerView recyclerviewType;
    Unbinder unbinder;

    private List<TypeBean> typeList = new ArrayList<>();

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

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2
                ,LinearLayoutManager.VERTICAL,false);
        recyclerviewType.setLayoutManager(layoutManager);
        TypeAdapter adapter = new TypeAdapter(getActivity(),typeList);
        recyclerviewType.setAdapter(adapter);

        adapter.setTypeItemOnclickListener(new TypeAdapter.OnTypeItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), BrowserActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
