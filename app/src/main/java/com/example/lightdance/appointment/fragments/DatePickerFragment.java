package com.example.lightdance.appointment.fragments;


import android.content.Context;
import android.icu.util.Calendar;
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

import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
//继承自会话碎片 使碎片以会话窗口的形式出现
public class DatePickerFragment extends DialogFragment {


    @BindView(R.id.datePicker)
    DatePicker datePicker;
    @BindView(R.id.img_data_select)
    ImageView imgDataSelect;
    Unbinder unbinder;

    private Calendar cal;
    private int year;
    private int month;
    private int day;

    private dateListener dateListener = null;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof dateListener){
            dateListener = (dateListener) context;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //去掉标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_data_picker, container, false);
        unbinder = ButterKnife.bind(this, view);

        //获取时间
        cal   = Calendar.getInstance();
        year  = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        day   = cal.get(Calendar.DAY_OF_MONTH);

        //datePicker初始化 设置初始日期 并添加监听
        datePicker.init(year, cal.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                month = monthOfYear + 1;
                day   = dayOfMonth;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.img_data_select)
    public void onViewClicked() {
        dateListener.dateSave(year,month,day);
        dismiss();
    }

    public interface dateListener{
        void dateSave(int year,int month,int day);
    }

}
