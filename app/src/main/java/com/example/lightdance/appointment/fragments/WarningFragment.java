package com.example.lightdance.appointment.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lightdance.appointment.R;
import com.example.lightdance.appointment.activities.LoginActivity;
import com.example.lightdance.appointment.activities.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WarningFragment extends Fragment {


    Unbinder unbinder;

    public WarningFragment() {
        // Required empty public constructor
    }

    public static WarningFragment newInstance() {
        WarningFragment fragment = new WarningFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_warning, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //点击监听
    @OnClick({R.id.tv_to_changefragment, R.id.tv_to_sighup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_to_changefragment:
                MainActivity activity = (MainActivity) getActivity();
                activity.changeNavigationSelected(R.id.menu_news);
                activity.changeFragment(2);
                break;
            case R.id.tv_to_sighup:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
