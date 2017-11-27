package com.example.lightdance.appointment.fragments;


import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lightdance.appointment.Model.BrowserMsgBean;
import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.AppointmentDetailActivity;
import com.example.lightdance.appointment.activities.BrowserActivity;
import com.example.lightdance.appointment.activities.UserInfoActivity;
import com.example.lightdance.appointment.adapters.BrowserMsgAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * @author 忘了
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.new_appointment)
    FloatingActionButton fab;
    @BindView(R.id.recyclerview_browse)
    RecyclerView recyclerView;

    private List<BrowserMsgBean> browserMsgBeen;
    private int typeCode = 0;
    private ProgressDialog progressDialog;

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
    public void onStart() {
        super.onStart();
        //当该Fragment被重新使用时将刷新一下该碎片数据
        initBrowserData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        unbinder = ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("请稍等");
        progressDialog.setMessage("加载中...");
        progressDialog.show();

        return view;
    }

    public void initBrowserData() {
        BmobQuery<BrowserMsgBean> query = new BmobQuery<>();
        query.addWhereEqualTo("typeCode",typeCode);
        query.setLimit(20);
        query.findObjects(new FindListener<BrowserMsgBean>() {
            @Override
            public void done(List<BrowserMsgBean> list, BmobException e) {
                if (e == null){
                    browserMsgBeen = list;
                    //绑定RecyclerView 并设置适配器
                    StaggeredGridLayoutManager layoutManager = new
                            StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    BrowserMsgAdapter adapter = new BrowserMsgAdapter(getActivity(),browserMsgBeen);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();

                    recyclerView.setOnScrollListener(new HideScrollListener());

                    //在这里实现Adapter的点击接口具体方法
                    adapter.setItemOnclickListener(new BrowserMsgAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            String objectId = browserMsgBeen.get(position).getObjectId();
                            Intent intent = new Intent(getActivity(), AppointmentDetailActivity.class);
                            intent.putExtra("objectId",objectId);
                            startActivity(intent);
                        }
                    });
                    adapter.setInviterOnClickListener(new BrowserMsgAdapter.OnInviterClickListener() {
                        @Override
                        public void onClick(int position) {
                            BmobQuery<BrowserMsgBean> query = new BmobQuery<>();
                            query.getObject(browserMsgBeen.get(position).getObjectId(), new QueryListener<BrowserMsgBean>() {
                                @Override
                                public void done(BrowserMsgBean browserMsgBean, BmobException e) {
                                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                                    intent.putExtra("objectId",browserMsgBean.getInviter());
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }else{
                    Toast.makeText(getActivity(),"查询失败 "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @OnClick(R.id.new_appointment)
    public void onViewClicked() {
        BrowserActivity activity = (BrowserActivity) getActivity();
        activity.changeFragment(2);
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

    public void sendSelectType(int typeCode){
        this.typeCode = typeCode;
    }



}
