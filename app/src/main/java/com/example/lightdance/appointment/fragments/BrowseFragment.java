package com.example.lightdance.appointment.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightdance.appointment.Model.BrowseMsgBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.BrowserMsgAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {
    
    private List<BrowseMsgBean> browseMsgBeen = DataSupport.findAll(BrowseMsgBean.class);

    public BrowseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        //初始化数据
        initAppointmentMsg();
        //绑定RecyclerView 并设置适配器
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_browse);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BrowserMsgAdapter adapter = new BrowserMsgAdapter(browseMsgBeen);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initAppointmentMsg() {

    }
}
