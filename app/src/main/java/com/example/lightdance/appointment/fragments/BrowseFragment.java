package com.example.lightdance.appointment.fragments;


import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
    @BindView(R.id.recyclerview_browse)
    RecyclerView recyclerView;

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

        //绑定RecyclerView 并设置适配器
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BrowserMsgAdapter adapter = new BrowserMsgAdapter(browseMsgBeen);
        this.recyclerView.setAdapter(adapter);

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
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
        int marginBottom = params.bottomMargin;
        ObjectAnimator.ofFloat(toolbar, "translationY", 0, -0).setDuration(200).start();
        ObjectAnimator.ofFloat(fab, "translationY", 0, fab.getHeight() + fab.getPaddingBottom() + marginBottom)
                .setDuration(200).start();
    }

    private void onShow() {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) fab.getLayoutParams();
        int marginBottom = params.bottomMargin;
        ObjectAnimator.ofFloat(toolbar, "translationY", -0, 0).setDuration(200).start();
        ObjectAnimator.ofFloat(fab, "translationY", fab.getHeight() + fab.getPaddingBottom() + marginBottom, 0)
                .setDuration(200).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
