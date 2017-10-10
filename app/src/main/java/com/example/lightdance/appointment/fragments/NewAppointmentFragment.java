package com.example.lightdance.appointment.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by LightDance on 2017/10/4.
 */

public class NewAppointmentFragment extends Fragment {

    @BindView(R.id.tv_activity_time)
    TextView tvActivityTime;
    Unbinder unbinder;
    @BindView(R.id.tv_newappointment_title)
    TextView tvNewappointmentTitle;
    @BindView(R.id.tv_activity_end_time)
    TextView tvActivityEndTime;
    @BindView(R.id.tv_activity_start_time)
    TextView tvActivityStartTime;
    @BindView(R.id.tv_activity_start_data)
    TextView tvActivityStartData;
    @BindView(R.id.tv_activity_end_data)
    TextView tvActivityEndData;

    private DataPickerFragment dataPickerFragment = new DataPickerFragment();
    private TimePickerFragment timePickerFragment = new TimePickerFragment();

    public NewAppointmentFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_appointment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_activity_end_time, R.id.tv_activity_start_time, R.id.tv_activity_start_data, R.id.tv_activity_end_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_end_time:
                //展示碎片会话窗口（dialog）
                timePickerFragment.show(getFragmentManager(),"TimePickerFragment");
                break;
            case R.id.tv_activity_start_time:
                timePickerFragment.show(getFragmentManager(),"TimePickerFragment");
                break;
            case R.id.tv_activity_start_data:
                dataPickerFragment.show(getFragmentManager(),"DataPickerFragment");
                break;
            case R.id.tv_activity_end_data:
                dataPickerFragment.show(getFragmentManager(),"DataPickerFragment");
                break;
        }
    }
}
