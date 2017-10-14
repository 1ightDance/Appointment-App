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
        BrowseMsgBean user1 = new BrowseMsgBean();
        user1.setTitle("双休日图书馆6楼讨论室自习");
        user1.setStartTime("17/10/21 8:00");
        user1.setEndTime("17/10/22 22:00");
        user1.setTypeIconId(R.drawable.ic_study);
        user1.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user1.setInviterIconId(R.mipmap.headshot_1);
        user1.setInviter("花落的速度");
        user1.setPlace("双休日图书馆6楼");
        user1.setPersonNumber("1/4人");
        user1.setPublishTime("17/10/15");
        user1.save();
        BrowseMsgBean user2 = new BrowseMsgBean();
        user2.setTitle("周六晚上五餐三楼狼人杀");
        user2.setStartTime("17/10/21 7:00");
        user2.setEndTime("17/10/21 22:00");
        user2.setTypeIconId(R.drawable.ic_brpg);
        user2.setContent("狼人杀杀起来啊！牌我自备！人均至少消费的那一杯算我头上，你们只管来！先到先得 12人局 顺便交波朋友");
        user2.setInviterIconId(R.mipmap.headshot_2);
        user2.setInviter("教皇");
        user2.setPlace("五餐三楼");
        user2.setPersonNumber("3/12人");
        user2.setPublishTime("17/10/18");
        user2.save();
        BrowseMsgBean user3 = new BrowseMsgBean();
        user3.setTitle("20号晚上弗雷德密室逃脱");
        user3.setStartTime("17/10/20 7:00");
        user3.setEndTime("17/10/20 22:00");
        user3.setTypeIconId(R.drawable.ic_game);
        user3.setContent("喜欢紧张刺激的密室逃脱 但是身边朋友都没兴趣 想来约一波兴趣相投的朋友一起去");
        user3.setInviterIconId(R.mipmap.headshot_3);
        user3.setInviter("花落的速度");
        user3.setPlace("双休日图书馆6楼");
        user3.setPersonNumber("1/4人");
        user3.setPublishTime("17/10/15");
        user3.save();
        BrowseMsgBean user4 = new BrowseMsgBean();
        user4.setTitle("双休日图书馆6楼讨论室自习");
        user4.setStartTime("17/10/21 8:00");
        user4.setEndTime("17/10/22 22:00");
        user4.setTypeIconId(R.drawable.ic_study);
        user4.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user4.setInviterIconId(R.mipmap.headshot_4);
        user4.setInviter("花落的速度");
        user4.setPlace("双休日图书馆6楼");
        user4.setPersonNumber("1/4人");
        user4.setPublishTime("17/10/15");
        user4.save();
        BrowseMsgBean user5 = new BrowseMsgBean();
        user5.setTitle("双休日图书馆6楼讨论室自习");
        user5.setStartTime("17/10/21 8:00");
        user5.setEndTime("17/10/22 22:00");
        user5.setTypeIconId(R.drawable.ic_study);
        user5.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user5.setInviterIconId(R.mipmap.headshot_5);
        user5.setInviter("花落的速度");
        user5.setPlace("双休日图书馆6楼");
        user5.setPersonNumber("1/4人");
        user5.setPublishTime("17/10/15");
        user5.save();
        BrowseMsgBean user6 = new BrowseMsgBean();
        user6.setTitle("双休日图书馆6楼讨论室自习");
        user6.setStartTime("17/10/21 8:00");
        user6.setEndTime("17/10/22 22:00");
        user6.setTypeIconId(R.drawable.ic_study);
        user6.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user6.setInviterIconId(R.mipmap.headshot_6);
        user6.setInviter("花落的速度");
        user6.setPlace("双休日图书馆6楼");
        user6.setPersonNumber("1/4人");
        user6.setPublishTime("17/10/15");
        user6.save();
        BrowseMsgBean user7 = new BrowseMsgBean();
        user7.setTitle("双休日图书馆6楼讨论室自习");
        user7.setStartTime("17/10/21 8:00");
        user7.setEndTime("17/10/22 22:00");
        user7.setTypeIconId(R.drawable.ic_study);
        user7.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user7.setInviterIconId(R.mipmap.headshot_7);
        user7.setInviter("花落的速度");
        user7.setPlace("双休日图书馆6楼");
        user7.setPersonNumber("1/4人");
        user7.setPublishTime("17/10/15");
        user7.save();
        BrowseMsgBean user8 = new BrowseMsgBean();
        user8.setTitle("双休日图书馆6楼讨论室自习");
        user8.setStartTime("17/10/21 8:00");
        user8.setEndTime("17/10/22 22:00");
        user8.setTypeIconId(R.drawable.ic_study);
        user8.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user8.setInviterIconId(R.mipmap.headshot_8);
        user8.setInviter("花落的速度");
        user8.setPlace("双休日图书馆6楼");
        user8.setPersonNumber("1/4人");
        user8.setPublishTime("17/10/15");
        user8.save();
        BrowseMsgBean user9 = new BrowseMsgBean();
        user9.setTitle("双休日图书馆6楼讨论室自习");
        user9.setStartTime("17/10/21 8:00");
        user9.setEndTime("17/10/22 22:00");
        user9.setTypeIconId(R.drawable.ic_study);
        user9.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user9.setInviterIconId(R.mipmap.headshot_9);
        user9.setInviter("花落的速度");
        user9.setPlace("双休日图书馆6楼");
        user9.setPersonNumber("1/4人");
        user9.setPublishTime("17/10/15");
        user9.save();
    }
}
