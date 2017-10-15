package com.example.lightdance.appointment.fragments;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightdance.appointment.Model.BrowseMsgBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.MainActivity;
import com.example.lightdance.appointment.adapters.BrowserMsgAdapter;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.lightdance.appointment.R.id.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.new_appointment)
    FloatingActionButton fab;
    private boolean added = false;

    private List<BrowseMsgBean> browseMsgBeen = DataSupport.findAll(BrowseMsgBean.class);

    public BrowseFragment() {
        // Required empty public constructor
    }

    public static BrowseFragment newInstance() {
        BrowseFragment fragment = new BrowseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        unbinder = ButterKnife.bind(this, view);

        //加载预览数据
        previewDataLoading();
        //绑定RecyclerView 并设置适配器
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_browse);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BrowserMsgAdapter adapter = new BrowserMsgAdapter(browseMsgBeen);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollListener(new HideScrollListener());

        return view;
    }

    @OnClick(R.id.new_appointment)
    public void onViewClicked() {
        MainActivity activity = (MainActivity) getActivity();
        activity.changeFragment(3);
    }

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
            if ((toolbarVisible && dy > 0) || !toolbarVisible && dy < 0) {
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

    private void onHide() {
        ObjectAnimator.ofFloat(toolbar, "translationY", 0, -0).setDuration(200).start();
        ObjectAnimator.ofFloat(fab, "translationY", 0, fab.getHeight() + fab.getPaddingBottom())
                .setDuration(200).start();
    }

    private void onShow() {
        ObjectAnimator.ofFloat(toolbar, "translationY", -0, 0).setDuration(200).start();
        ObjectAnimator.ofFloat(fab, "translationY", fab.getHeight() + fab.getPaddingBottom(), 0)
                .setDuration(200).start();
    }

    private void previewDataLoading() {
        //判断是否加载过预览数据
        if (added == false) initAppointmentMsg();
        added = true;
    }

    private void initAppointmentMsg() {
        BrowseMsgBean user1 = new BrowseMsgBean();
        user1.setTitle("双休日图书馆6楼讨论室自习");
        user1.setTypeIconId(R.drawable.ic_study);
        user1.setContent("emmmmmm...希望能找几个人一起自习 互相监督！互相进步！自己自习总是坚持不下来");
        user1.setInviterIconId(R.mipmap.headshot_1);
        user1.setInviter("花落的速度");
        user1.setPlace("双休日图书馆6楼");
        user1.setPersonNumber("1/4人");
        user1.save();
        BrowseMsgBean user2 = new BrowseMsgBean();
        user2.setTitle("周六晚上五餐三楼狼人杀");
        user2.setTypeIconId(R.drawable.ic_brpg);
        user2.setContent("狼人杀杀起来啊！牌我自备！人均至少消费的那一杯算我头上，你们只管来！先到先得 12人局 顺便交波朋友");
        user2.setInviterIconId(R.mipmap.headshot_2);
        user2.setInviter("教皇");
        user2.setPlace("五餐三楼");
        user2.setPersonNumber("3/12人");
        user2.save();
        BrowseMsgBean user3 = new BrowseMsgBean();
        user3.setTitle("20号晚上弗雷德密室逃脱");
        user3.setTypeIconId(R.drawable.ic_game);
        user3.setContent("喜欢紧张刺激的密室逃脱 但是身边朋友都没兴趣 想来约一波兴趣相投的朋友一起去（门票费自理）");
        user3.setInviterIconId(R.mipmap.headshot_3);
        user3.setInviter("caozh");
        user3.setPlace("杭电生活区东门集合一起出发");
        user3.setPersonNumber("6/10人");
        user3.save();
        BrowseMsgBean user4 = new BrowseMsgBean();
        user4.setTitle("云南自由行");
        user4.setTypeIconId(R.drawable.ic_travel);
        user4.setContent("有计划去云南旅游的或者想出去旅游但不知道去哪里的朋友嘛？运动会期间会有至少四天假期，可以出去玩一玩~跟我一起说走就走吧！费用自行承担，装备自行准备");
        user4.setInviterIconId(R.mipmap.headshot_4);
        user4.setInviter("大毛");
        user4.setPlace("云南昆明");
        user4.setPersonNumber("1/8人");
        user4.save();
        BrowseMsgBean user5 = new BrowseMsgBean();
        user5.setTitle("世纪难题——减肥");
        user5.setTypeIconId(R.drawable.ic_sports);
        user5.setContent("求一起减肥的胖友跑步，每周五六日晚跑一个小时，恶劣天气除外 有兴趣的戳进来加我Q。。。");
        user5.setInviterIconId(R.mipmap.headshot_5);
        user5.setInviter("蛋白质");
        user5.setPlace("杭电东操场");
        user5.setPersonNumber("1/2人");
        user5.save();
        BrowseMsgBean user6 = new BrowseMsgBean();
        user6.setTitle("约饭");
        user6.setTypeIconId(R.drawable.ic_dining);
        user6.setContent("本人有一张高沙胖哥俩满500可享5折的优惠券，自己吃不到500，独乐乐不如众乐乐顺便交朋友，费用AA");
        user6.setInviterIconId(R.mipmap.headshot_6);
        user6.setInviter("吉瑞斯的指环");
        user6.setPlace("1餐门口17：30集合一起过去");
        user6.setPersonNumber("1/8人");
        user6.save();
        BrowseMsgBean user7 = new BrowseMsgBean();
        user7.setTitle("寻参加完宁波造音节返校同路小伙伴");
        user7.setTypeIconId(R.drawable.ic_others);
        user7.setContent("找造音节结束接着赶回学校的嘻哈爱好者同路，到杭州后也比较晚了，可以拼车回学校，费用AA");
        user7.setInviterIconId(R.mipmap.headshot_7);
        user7.setInviter("契鸽");
        user7.setPlace("宁波火车站");
        user7.setPersonNumber("1/4人");
        user7.save();
        BrowseMsgBean user8 = new BrowseMsgBean();
        user8.setTitle("约22号电影《羞羞的铁拳》");
        user8.setTypeIconId(R.drawable.ic_movies);
        user8.setContent("本来和室友一起买了22号《羞羞的铁拳》，但是室友临时有事，转让电影票 价格可小刀");
        user8.setInviterIconId(R.mipmap.headshot_8);
        user8.setInviter("ZhengHQ");
        user8.setPlace("中影星城都尚影院");
        user8.setPersonNumber("1/2人");
        user8.save();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
