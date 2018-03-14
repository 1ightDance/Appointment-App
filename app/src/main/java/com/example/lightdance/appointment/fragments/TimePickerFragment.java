package com.example.lightdance.appointment.fragments;


import android.content.Context;
import java.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
//继承自会话碎片 使碎片以会话窗口的形式出现
public class TimePickerFragment extends DialogFragment {

    @BindView(R.id.datePicker)
    DatePicker datePicker;
    @BindView(R.id.timePicker)
    TimePicker timePicker;
    @BindView(R.id.img_time_select)
    ImageView imgTimeSelect;
    Unbinder unbinder;

    private Calendar cal;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    private timeListener mTimeListener = null;

    public TimePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //判断上下文是否为timeListener实例
        if (context instanceof timeListener){
            mTimeListener = (timeListener) context;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //去掉标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        unbinder = ButterKnife.bind(this, view);

        //获取时间
        cal   = Calendar.getInstance();
        year  = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        day   = cal.get(Calendar.DAY_OF_MONTH);
        hour  = cal.get(Calendar.HOUR_OF_DAY);
        min   = cal.get(Calendar.MINUTE);

        //datePicker初始化 设置初始日期 并添加监听
        datePicker.init(year, cal.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                month = monthOfYear + 1;
                day   = dayOfMonth;
            }
        });

        //设置时钟是否显示24小时制
        timePicker.setIs24HourView(true);

        //设置时间选择监听
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min  = minute;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.img_time_select)
    public void onViewClicked() {
        mTimeListener.saveSelectTime(year,month,day,hour,min);
        dismiss();
    }

    public interface timeListener{
        void saveSelectTime(int year,int month,int day,int hour,int minute);
    }
}
