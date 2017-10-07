package com.example.lightdance.appointment.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightdance.appointment.R;

import butterknife.ButterKnife;

/**
 * Created by LightDance on 2017/10/5.
 */

public class NewsFragment extends Fragment{

    //目前是预览，到时候会从数据库读取数据


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //将碎片膨胀成视图(View)
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
}
