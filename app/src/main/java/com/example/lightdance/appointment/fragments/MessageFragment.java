package com.example.lightdance.appointment.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightdance.appointment.Model.MessageBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.adapters.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private List<MessageBean> messageBeanList = new ArrayList<>();

    public MessageFragment() {
        // Required empty public constructor
    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this,view);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_message);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        MessageAdapter adapter = new MessageAdapter(messageBeanList);
        recyclerView.setAdapter(adapter);

        initMessageData();

        return view;
    }

    private void initMessageData() {
        MessageBean msg1 = new MessageBean(R.mipmap.ic_mode_edit_white,"12:25","消息通知","尊敬的用户您好！您应约的#周六晚狼人杀#由于发起人个人原因已取消，对您造成的干扰非常抱歉！");
        messageBeanList.add(msg1);
        MessageBean msg2 = new MessageBean(R.mipmap.ic_mode_edit_white,"10:32","消息通知","您已成功应约#周六晚狼人杀#，请准时出席。如遇情况变更，我们将会第一时间通知到您。");
        messageBeanList.add(msg2);
    }

}
