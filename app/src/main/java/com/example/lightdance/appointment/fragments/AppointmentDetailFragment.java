package com.example.lightdance.appointment.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lightdance.appointment.Model.BrowseMsgBean;
import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AppointmentDetailFragment extends Fragment {


    @BindView(R.id.tv_detailed_info_title)
    TextView tvDetailedInfoTitle;
    @BindView(R.id.tv_detailed_info_place)
    TextView tvDetailedInfoPlace;
    @BindView(R.id.tv_detailed_info_starttime)
    TextView tvDetailedInfoStarttime;
    @BindView(R.id.tv_detailed_info_endtime)
    TextView tvDetailedInfoEndtime;
    @BindView(R.id.tv_detailed_info_description)
    TextView tvDetailedInfoDescription;
    @BindView(R.id.tv_detailed_info_connection)
    TextView tvDetailedInfoConnection;
    Unbinder unbinder;

    private BrowseMsgBean browseMsgBean;

    public AppointmentDetailFragment() {
        // Required empty public constructor
    }

    public static AppointmentDetailFragment newInstance() {
        AppointmentDetailFragment fragment = new AppointmentDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_user_info, container, false);
        unbinder = ButterKnife.bind(this, view);

        initMsg();

        return view;
    }

    private void initMsg() {
        tvDetailedInfoTitle.setText(browseMsgBean.getTitle());
        tvDetailedInfoPlace.setText(browseMsgBean.getPlace());
        tvDetailedInfoStarttime.setText(browseMsgBean.getStartTime());
        tvDetailedInfoEndtime.setText(browseMsgBean.getEndTime());
        tvDetailedInfoDescription.setText(browseMsgBean.getContent());
        tvDetailedInfoConnection.setText(browseMsgBean.getContactWay());
    }

    public void setClickedMsg(BrowseMsgBean browseMsgBean) {
        this.browseMsgBean = browseMsgBean;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.detailed_info_take_part_in)
    public void onViewClicked() {
    }
}
