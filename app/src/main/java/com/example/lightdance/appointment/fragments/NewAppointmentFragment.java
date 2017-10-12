package com.example.lightdance.appointment.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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

    Unbinder unbinder;
    @BindView(R.id.tv_newappointment_title)
    TextView tvNewappointmentTitle;
    @BindView(R.id.tv_activity_end_time)
    TextView tvActivityEndTime;
    @BindView(R.id.tv_activity_start_time)
    TextView tvActivityStartTime;
    @BindView(R.id.tv_activity_start_date)
    TextView tvActivityStartDate;
    @BindView(R.id.tv_activity_end_date)
    TextView tvActivityEndDate;

    private int dateChange = 0;

    //获取日期选择器和时间选择器实例
    private DatePickerFragment datePickerFragment = new DatePickerFragment();
    private TimePickerFragment timePickerFragment = new TimePickerFragment();

    public NewAppointmentFragment() {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    @OnClick({R.id.tv_activity_end_time, R.id.tv_activity_start_time, R.id.tv_activity_start_date, R.id.tv_activity_end_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_activity_end_time:
                //展示碎片会话窗口（dialog）
                timePickerFragment.show(getFragmentManager(),"TimePickerFragment");
                break;
            case R.id.tv_activity_start_time:
                timePickerFragment.show(getFragmentManager(),"TimePickerFragment");
                break;
            case R.id.tv_activity_start_date:
                datePickerFragment.show(getFragmentManager(),"DatePickerFragment");
                dateChange = 1;
                break;
            case R.id.tv_activity_end_date:
                datePickerFragment.show(getFragmentManager(),"DatePickerFragment");
                dateChange = 2;
                break;
        }
    }

    public void setDate(int yearSelect,int monthSelect,int daySelect){
        if (dateChange == 1){
            setStartDate(yearSelect,monthSelect,daySelect);
        }
        if (dateChange == 2){
            setEndDate(yearSelect,monthSelect,daySelect);
        }
        dateChange = 0;
    }

    public void setStartDate(int year,int month,int day){
        if (month < 10 && day >= 10) {
            tvActivityStartDate
                    .setText(year + "年0" + month + "月" + day + "日");
        }
        if (day < 10 && month >= 10){
            tvActivityStartDate
                    .setText(year + "年" + month + "月0" + day + "日");
        }
        if (day < 10 && month < 10){
            tvActivityStartDate
                    .setText(year + "年0" + month + "月0" + day + "日");
        }
        if (day >= 10 && month >= 10){
            tvActivityStartDate
                    .setText(year + "年" + month + "月" + day + "日");
        }
    }

    public void setEndDate(int year,int month,int day){
        if (month < 10 && day >= 10) {
            tvActivityEndDate
                    .setText(year + "年0" + month + "月" + day + "日");
        }
        if (day < 10 && month >= 10){
            tvActivityEndDate
                    .setText(year + "年" + month + "月0" + day + "日");
        }
        if (day < 10 && month < 10){
            tvActivityEndDate
                    .setText(year + "年0" + month + "月0" + day + "日");
        }
        if (day >= 10 && month >= 10){
            tvActivityEndDate
                    .setText(year + "年" + month + "月" + day + "日");
        }
    }

    public void setStartTime(int hour,int min){

    }

    public void setEndTime(int hour,int min){

    }
}
