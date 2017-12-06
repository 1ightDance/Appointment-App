package com.example.lightdance.appointment.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.CommentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentListFragment extends Fragment {


    @BindView(R.id.toolbar_comment_list_fragment)
    Toolbar toolbar;
    Unbinder unbinder;

    public CommentListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        toolbar.setTitle("未反馈活动列表");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.mipmap.ic_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentActivity commentActivity = (CommentActivity) getActivity();
                commentActivity.finish();
            }
        });

        return view;
    }

    public static CommentListFragment newInstance() {
        CommentListFragment fragment = new CommentListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
