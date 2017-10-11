package com.example.lightdance.appointment.fragments;


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
import android.widget.Toast;

import com.example.lightdance.appointment.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
//继承自会话碎片 使碎片以会话窗口的形式出现
public class DataPickerFragment extends DialogFragment {


    @BindView(R.id.datePicker)
    DatePicker datePicker;
    @BindView(R.id.img_data_select)
    ImageView imgDataSelect;
    Unbinder unbinder;

    private Calendar cal;
    private int year;
    private int month;
    private int day;

    public DataPickerFragment() {
        // Required empty public constructor
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

        //datePicker初始化 设置初始日期
        datePicker.init(year, cal.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getActivity(),"日期：" + year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.img_data_select)
    public void onViewClicked() {
        Toast.makeText(getActivity(),"日期：" + year + "年" + month + "月" + day + "日",Toast.LENGTH_SHORT).show();
//        DataListener dataListener = (DataListener) getActivity();
//        dataListener.DataSave(year,month,day);
    }

    public interface DataListener{
        void DataSave(int year,int month,int day);
    }

}
